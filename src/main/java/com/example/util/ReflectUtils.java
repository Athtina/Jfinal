package com.example.util;

import aj.org.objectweb.asm.ClassReader;
import aj.org.objectweb.asm.ClassVisitor;
import aj.org.objectweb.asm.Label;
import aj.org.objectweb.asm.MethodVisitor;
import aj.org.objectweb.asm.Opcodes;
import aj.org.objectweb.asm.Type;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/12 9:27
 */
public class ReflectUtils {

  public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
    final Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes == null || parameterTypes.length == 0) {
      return null;
    }
    final Type[] types = new Type[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      types[i] = Type.getType(parameterTypes[i]);
    }
    final List<String> parameterNameList = new ArrayList<String>();

    String className = clazz.getName();
    int lastDotIndex = className.lastIndexOf(".");
    className = className.substring(lastDotIndex + 1) + ".class";
    InputStream is = clazz.getResourceAsStream(className);
    try {
      ClassReader classReader = new ClassReader(is);
      classReader.accept(new ClassVisitor(Opcodes.ASM4) {
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature,
            String[] exceptions) {
          // 只处理指定的方法
          Type[] argumentTypes = Type.getArgumentTypes(desc);
          if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
            return super.visitMethod(access, name, desc, signature,
                exceptions);
          }
          return new MethodVisitor(Opcodes.ASM4) {
            @Override
            public void visitLocalVariable(String name, String desc, String signature, Label start,
                Label end, int index) {
              // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
              if (Modifier.isStatic(method.getModifiers())) {
                parameterNameList.add(index,name);
              } else if (index > 0) {
                parameterNameList.add(index-1,name);
              }
            }
          };

        }
      }, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return parameterNameList.toArray(new String[parameterNameList.size()]);
  }

}

package com.example.common.db;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/7 15:44
 */
@Configuration
public class MyBatisConfig implements TransactionManagementConfigurer {

//    @Value("${databaseRW.name}")
//    private String name;

  @Value("${databaseRW.driverClassName}")
  private String className;

  @Value("${databaseRW.url}")
  private String url;

  @Value("${databaseRW.username}")
  private String username;

  @Value("${databaseRW.password}")
  private String password;

  @Value("${databaseRW.maxPoolSize}")
  private int maxPoolSize;

  @Value("${databaseRW.minPoolSize}")
  private int minPoolSize;

//  @Value("${databaseRW.initialPoolSize}")
//  private int initialPoolSize;

  @Value("${databaseRW.maxIdleTime}")
  private int maxIdleTime;

  /**
   * 配置dataSource，使用C3P0连接池
   */
  @Bean
  @Primary
  public DataSource dataSource1() throws PropertyVetoException {

    ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDataSourceName(name);
    dataSource.setDriverClass(className);
    dataSource.setJdbcUrl(url);
    dataSource.setUser(username);
    dataSource.setPassword(password);
    dataSource.setMaxPoolSize(maxPoolSize);
    dataSource.setMinPoolSize(minPoolSize);
//    dataSource.setInitialPoolSize(initialPoolSize);
    dataSource.setMaxIdleTime(maxIdleTime);
    return dataSource;
  }

  /**
   * 配置SqlSessionFactory： - 创建SqlSessionFactoryBean，并指定一个dataSource； -
   * 设置这个分页插件：https://github.com/pagehelper/Mybatis-PageHelper； - 指定mapper文件的路径；
   */
  @Bean
  @Primary
  public SqlSessionFactory sqlSessionFactory1() throws Exception {

    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource1());
    // 分页插件
//    PageInterceptor pageInterceptor = new PageInterceptor();
//    PageHelper pageHelper = new PageHelper();
//    Properties properties = new Properties();
//    properties.setProperty("dialect", "mysql");
//    properties.setProperty("reasonable", "false");
//    properties.setProperty("pageSizeZero", "true");
//    pageHelper.setProperties(properties);
//    pageInterceptor.setProperties(properties);
//    bean.setPlugins(new Interceptor[]{pageInterceptor});
    try {
      // 指定mapper xml目录
      ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
      bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
      bean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
      return bean.getObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Transaction 相关配置 因为有两个数据源， 所有使用ChainedTransactionManager把两个DataSourceTransactionManager包括在一起。
   */
  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    DataSourceTransactionManager dtm1;
    ChainedTransactionManager ctm = null;
    try {
      dtm1 = new DataSourceTransactionManager(dataSource1());

      ctm = new ChainedTransactionManager(dtm1);
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }

    return ctm;
  }
}
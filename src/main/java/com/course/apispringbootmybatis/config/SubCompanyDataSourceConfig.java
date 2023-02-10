package com.course.apispringbootmybatis.config;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@MapperScan(basePackages = {
    "com.course.apispringbootmybatis.infrastructure.mapper.sub_company"
}, sqlSessionTemplateRef = "subCompanySqlSessionTemplate")
public class SubCompanyDataSourceConfig {

  @Primary
  @Bean(name = "subCompanyDataSourceProperties")
  @ConfigurationProperties(prefix = "spring.datasource.sub-company")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "subCompanySqlInitializationProperties")
  @ConfigurationProperties(prefix = "spring.sql.init.sub-company")
  public SqlInitializationProperties sqlInitializationProperties() {
    return new SqlInitializationProperties();
  }

  @Primary
  @Bean(name = "subCompanyDataSource")
  public DataSource dataSource(
      @Qualifier("subCompanyDataSourceProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Primary
  @Bean("subCompanySqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("subCompanyDataSource") DataSource dataSource,
      ApplicationContext context) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath:mapper/sub_company/*.xml"));
    bean.setConfigLocation(context.getResource(
        Optional.ofNullable(context.getEnvironment().getProperty("mybatis.config-location"))
            .orElse("")));
    return bean.getObject();
  }

  @Primary
  @Bean("subCompanySqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("subCompanySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Primary
  @Bean("subCompanyDataSourceInitializer")
  public DataSourceInitializer dataSourceInitializer(
      @Qualifier("subCompanyDataSource") DataSource dataSource,
      @Qualifier("subCompanySqlInitializationProperties") SqlInitializationProperties sqlInitializationProperties) {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    ResourceLoader loader = new DefaultResourceLoader();
    Stream.concat(Optional.ofNullable(sqlInitializationProperties.getSchemaLocations()).stream()
            .flatMap(Collection::stream),
        Optional.ofNullable(sqlInitializationProperties.getDataLocations()).stream()
            .flatMap(Collection::stream)).map(loader::getResource).forEach(populator::addScript);
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }

  @Primary
  @Bean("subCompanyDataSourceTransactionManager")
  public DataSourceTransactionManager dataSourceTransactionManager(
      @Qualifier("subCompanyDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}

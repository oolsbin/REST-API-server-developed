//package config;
//
//import javax.activation.DataSource;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//@Configuration
//@PropertySource("classpath:/application.properties") //①
//public class DatabaseConfiguration {
//
//    //②
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DatasourceConfig hikariConfig() {
//        return new HikariConfig();
//    }
//
//    //③
//    @Bean
//    public DataSource dataSource() throws Exception {
//        DataSource dataSource = new HikariDataSource(hikariConfig());
//        System.out.println(dataSource.toString());
//        return dataSource;
//    }
//}
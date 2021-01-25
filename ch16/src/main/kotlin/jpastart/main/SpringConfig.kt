package jpastart.main

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["jpastart.reserve"])
class SpringConfig {

    @Bean(destroyMethod = "close")
    fun dataSource(): ComboPooledDataSource = ComboPooledDataSource().apply {
        driverClass = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy"
        jdbcUrl = "jdbc:log4jdbc:mariadb://localhost:3306/jpastart?characterEncoding=utf8"
//        driverClass = "org.mariadb.jdbc.Driver"
//        jdbcUrl = "jdbc::mariadb://localhost:3306/jpastart?characterEncoding=utf8"
        user = "jpauser"
        password = "jpapass"
    }

    @Bean
    fun emfFactory(): LocalContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSource()
        persistenceUnitName = "jpastart"
        jpaVendorAdapter = HibernateJpaVendorAdapter().apply {
            setDatabase(Database.MYSQL)
            setShowSql(false)
        }
    }

    @Bean
    fun transactionManager(emf: EntityManagerFactory) = JpaTransactionManager(emf)

}

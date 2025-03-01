# homeautomation-spring-rest

This is a REST extension of the [homeautomation-spring-core](https://github.com/davemeier82/homeautomation-spring-core/blob/main/README.md) home automation framework.

Features

* REST Interface to trigger events (e.g. turn on light)
* REST Interface to configure the system (e.g. add/update/delete devices)

## Usage

Checkout the detailed usage in the Demo: [homeautomation-demo](https://github.com/davemeier82/homeautomation-demo/blob/main/README.md)

```xml

<project>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.github.davemeier82.homeautomation</groupId>
                <artifactId>homeautomation-bom</artifactId>
                <version>${homeautomation-bom.version}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>io.github.davemeier82.homeautomation</groupId>
            <artifactId>homeautomation-spring-core</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.davemeier82.homeautomation</groupId>
            <artifactId>homeautomation-spring-rest</artifactId>
        </dependency>
    </dependencies>
</project>
```
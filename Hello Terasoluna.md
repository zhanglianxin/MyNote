# Hello Terasoluna

工程结构：
```
Java Resources
|--sources
   |-- web
       |-- common
           |-- controller
           |-- uvo
       |-- helloworld
           |-- blogic
               |-- HelloWorldBlogic.java
           |-- dto
               |-- HelloWorldInput.java
               |-- HelloWorldOutput.java
           |-- form
               |-- HelloWorldForm.java
   |-- sqlMap.xml
|-- webapps
    |-- helloworld
    	|-- helloworld.jsp
    |-- META-INF
        |-- context.xml
    |-- WEB-INF
        |-- helloworld
            |-- blogic-helloworld-io.xml
            |-- helloworldContext.xml
            |-- struts-helloworld-config.xml
            |-- validation-helloworld.xml
        |-- applicationContext.xml
        |-- blogic-io-rules.xml
        |-- blogic-io.xml
        |-- commonContext.xml
        |-- moduleContext.xml
        |-- sqlMapConfig.xml
        |-- struts-config.xml
        |-- web.xml
    |-- error.jsp
    |-- index.jsp
    |-- welcome.jsp
```
`web.xml`
```xml
<servlet>
	<init-param>
		<param-name>config</param-name>
		<param-value>
			/WEB-INF/struts-config.xml,
			/WEB-INF/helloworld/struts-helloworld-config.xml
		</param-value>
	</init-param>
</servlet>
```

`struts-config.xml`
```xml
<plug-in className="org.springframework.web.struts.ContextLoaderPlugIn">
	<set-property
		property="contextConfigLocation"
		value="/WEB-INF/moduleContext.xml,
		/WEB-INF/helloworld/helloworldContext.xml" />
</plug-in>


<plug-in className="org.apache.struts.validator.ValidatorPlugIn">
	<set-property
		property="pathnames"
		value="/WEB-INF/validator-rules.xml,
		/WEB-INF/validator-rules-ex.xml,
		/WEB-INF/validation.xml,
		/WEB-INF/helloworld/validation-helloworld.xml" />
</plug-in>

<plug-in className="jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn">
	<set-property
		property="resources"
		value="/WEB-INF/blogic-io.xml,
		/WEB-INF/helloworld/blogic-helloworld-io.xml" />
	<set-property
		property="digesterRules"
		value="/WEB-INF/blogic-io-rules.xml" />
	<set-property
		property="mapperClass"
		value="jp.terasoluna.fw.service.thin.BLogicMapper" />
</plug-in>
```

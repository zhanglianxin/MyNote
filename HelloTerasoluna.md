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

`struts-helloworld-config.xml`
```xml
<struts-config>
	<form-beans>
		<form-bean name="_helloWorldForm" 
                   type="jp.terasoluna.thin.tutorial.web.helloworld.form.HelloWorldForm" />
	</form-beans>
	<action-mappings type="jp.terasoluna.fw.web.struts.action.ActionMappingEx" >
		<action path="/logon/logonSCR" 
                name="_helloWorldForm" 
                scope="session" 
                parameter="/logon/helloWorldBL.do" />
		<action path="/logon/helloWorldBL" 
                name="_helloWorldForm" 
                scope="session" 
                validate="false" 
                input="/logon/logoff.do">
			<forward name="success" path="/helloworld/helloworld.jsp" />
		</action>
	</action-mappings>
</struts-config>
```

`helloworldContext.xml`
```xml
<beans>
	<bean name="/logon/logonSCR" scope="singleton" 
          class="jp.terasoluna.fw.web.struts.actions.ForwardAction" />
	<bean name="/logon/helloWorldBL" scope="singleton" 
          class="jp.terasoluna.fw.web.struts.actions.BLogicAction">
		<property name="businessLogic" ref="HelloWorldBLogic" />
	</bean>
	<bean id="HelloWorldBLogic" scope="prototype" 
          class="jp.terasoluna.thin.tutorial.web.helloworld.blogic.HelloWroldBLogic">
		<property name="queryDAO" ref="queryDAO" />
</beans>
```

`blogic-helloworld-io.xml`
```xml
<blogic-io>
	<action path="/logon/helloWorldBL">
	<blogic-params bean-name="jp.terasoluna.thin.tutorial.web.helloworld.dto.HelloWorld.Input" />
	<blogic-result>
		<set-property property="helloWorld" blogic-property="helloWorld" dest="form" />
	</blogic-result>
</blogic-io>
```

`sqlMap.xml`
<sqlMap namespace="helloworld">
	<select id="helloWorld" parameterClass="jp.terasoluna.thin.tutorial.web.helloworld.dto.HelloWorldInput"	resultClass="jp.terasoluna.thin.tutorial.web.helloworld.dto.HelloWorldOutput">
		SELECT 'helloworld' AS HELLOWORLD FROM DUAL
	</select>
</sqlMap>

`HelloWorldBLogic`
```java
public class HelloWorldBLogic implements BLogic<HelloWorldInput> {
	private QueryDAO queryDAO;

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public BLogicResult execute(HelloWorldInput param) {
		BLogicResult result = new BLogicResult();

		HelloWorldOutput output = queryDAO.executeForObject("helloworld", param, HelloWorldOutput.class);
		result.setResultObject(output);
		result.setResultString("success");

		return result;
	}
}
```

package ;


<#list typeList as type>
import ${type};
</#list>



public class ${fileName} {

<#list coList as key>
    private ${key.simpleType} ${key.alias};<#if key.coComment??&&key.coComment !=''>//${key.coComment}</#if>
 </#list>
   
<#list coList as key>
    public ${key.simpleType} get${key.alias?cap_first}() {
        return ${key.alias};
    }

    public void set${key.alias?cap_first}(${key.simpleType} ${key.alias}) {
        this.${key.alias} = ${key.alias};
    }
 </#list>  
	
}

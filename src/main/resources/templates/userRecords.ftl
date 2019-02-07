<#import "parts/common.ftl" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/recordEdit.ftl" />
    </#if>
    <#include "parts/recordList.ftl" />
</@c.page>
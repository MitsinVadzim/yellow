<#include "security.ftl">

<div class="card-columns">
    <#list records as record>
        <div class="card my-3">
            <#if record.filename??>
                <img src="/img/${record.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${record.distance}</span><br>
                <i>${record.time}</i><br>
                <i>${record.date}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-records/${record.author.id}">${record.authorName}</a>
                <#if record.author.id == currentUserId>
                    <a href="/user-records/${record.author.id}?record=${record.id}" class="btn btn-primary">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    <#else >
        No record
    </#list>
</div>
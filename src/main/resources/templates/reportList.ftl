<#import "parts/common.ftl" as c>

<@c.page>
    <div class="card-columns">
        <#if reports??>
            <#list reports as report>
                <div class="card my-3">
                    <div class="m-2">
                        Week ${report?index + 1}<br>
                        <span>Av.Speed:${report.avSpeed}</span>
                        <span>Av.Time:${report.avTime}</span><br>
                        <i>Total Distance:${report.totalDistance}</i>
                    </div>
                </div>
            </#list>
        <#else >
            No reports
        </#if>

    </div>
</@c.page>
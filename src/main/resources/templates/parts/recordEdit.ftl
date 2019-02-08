<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Record Edit
</a>
<div class="collapse <#if record??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="distance" placeholder="Enter distance" class="form-control ${(distanceError??)?string('is-invalid', '')}" value="<#if tempRecord??>${tempRecord.distance}</#if>">
                <#if distanceError??>
                    <div class="invalid-feedback">
                        ${distanceError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="time" placeholder="Enter running time" class="form-control ${(timeError??)?string('is-invalid', '')}" value="<#if tempRecord??>${tempRecord.time}</#if>">
                <#if timeError??>
                    <div class="invalid-feedback">
                        ${timeError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="date" placeholder="YYYY-MM-DD" class="form-control ${(dateError??)?string('is-invalid', '')}" value="<#if tempRecord??>${tempRecord.date}</#if>">
                <#if dateError??>
                    <div class="invalid-feedback">
                        ${dateError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="hidden" name="id" value="<#if tempRecord??>${tempRecord.id}</#if>">
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Create</button>
            </div>
        </form>
    </div>
</div>
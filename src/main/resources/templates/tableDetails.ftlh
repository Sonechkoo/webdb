<#import "common.ftlh" as c>

<@c.page>
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12" style="margin: auto">
                    <div class="card" style="padding: 10px">
                        <div class="header" style="padding-bottom: 8px">
                            <h4 class="title">Rows in table "${table.name}"</h4>
                            <div style="display: flex">
                                <a href="/base/${table.base.name}/table/${table.name}/rows/create" class="btn btn-info">Add Row</a>
                                <form method="post" action="/base/${table.base.name}/table/${table.name}/sort" style="margin-left: 10px; display: flex">
                                    <button type="submit" class="btn btn-warning">Sort</button>
                                    <div class="form-group w-100" style="width: 300px; height: 10px; margin-left: 10px">
                                        <select name="attribute_select" class="form-control" style="height: 38px;">
                                            <#list table.attributes as attribute>
                                                <option name="option" value="${attribute.name}">${attribute.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </form>

                                <a href="/base/${table.base.name}/tables" class="btn btn-secondary" style="margin-left: 70%">Back</a>
                            </div>
                        </div>

                        <div class="content table-responsive table-full-width">
                            <table class="table table-striped">
                                <thead>
                                <#list table.attributes as attribute>
                                    <th>${attribute.name}</th>
                                </#list>
                                <th></th>
                                <th></th>
                                </thead>
                                <tbody>
                                <#list table.rows as row>
                                    <tr>
                                        <#list row.values as value>
                                            <td>${value}</td>
                                        </#list>
                                        <td><a href="/base/${table.base.name}/table/${table.name}/rows/${row.id}/edit" class="btn btn-primary">Edit</a></td>
                                        <td>
                                            <form method="post" action="/base/${table.base.name}/table/${table.name}/rows/${row.id}/delete">
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@c.page>
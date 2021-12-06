<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<h1>文件上传</h1>
<form action="/mySpringMvc/file/upload" method="post" enctype="multipart/form-data">
    请选择文件： <input type="file" name="file">
    <br/><br/>
    <input type="submit" value="上 传">
</form>
</body>
</html>

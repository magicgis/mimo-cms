AND title like „someTitle‟
这个查询也会失败。这个问题不能简单的用条件来解决，如果你从来没有这样写过，那么你以后也不会这样来写。
MyBatis有一个简单的处理，这在90%的情况下都会有用。而在不能使用的地方，你可以自定义处理方式。加上一个简单的改变，所有事情都会顺利进行：
<select id=”findActiveBlogLike”
parameterType=”Blog” resultType=”Blog”>
SELECT * FROM BLOG
<where>
<if test=”state != null”>
state = #{state}
</if>
<if test=”title != null”>
AND title like #{title}
</if>
<if test=”author != null and author.name != null”>
AND title like #{author.name}
</if>
</where>
</select>
where元素知道如果由被包含的标记返回任意内容，就仅仅插入“WHERE”。而且，如果以“AND”或“OR”开头的内容，那么就会跳过WHERE不插入。
如果where元素没有做出你想要的，你可以使用trim元素来自定义。比如，和where元素相等的trim元素是：
<trim prefix="WHERE" prefixOverrides="AND |OR ">
…
</trim>
overrides属性采用管道文本分隔符来覆盖，这里的空白也是重要的。它的结果就是移除在overrides属性中指定的内容，插入在with属性中的内容。
和动态更新语句相似的解决方案是set。set元素可以被用于动态包含更新的列，而不包含不需更新的。比如：
<update id="updateAuthorIfNecessary"
parameterType="domain.blog.Author">
update Author
<set>
<if test="username != null">username=#{username},</if>
<if test="password != null">password=#{password},</if>
<if test="email != null">email=#{email},</if>
<if test="bio != null">bio=#{bio}</if>
</set>
where id=#{id}
</update>
这里，set元素会动态前置SET关键字，而且也会消除任意无关的逗号，那也许在应用条件之后来跟踪定义的值。
如果你对和这相等的trim元素好奇，它看起来就是这样的：
<trim prefix="SET" suffixOverrides=",">
…
</trim>
注意这种情况下我们覆盖一个后缀，而同时也附加前缀。
foreach
另外一个动态SQL通用的必要操作是迭代一个集合，通常是构建在IN条件中的。比如：
<select id="selectPostIn" resultType="domain.blog.Post">
SELECT *
FROM POST P
WHERE ID in
<foreach item="item" index="index" collection="list"
open="(" separator="," close=")">
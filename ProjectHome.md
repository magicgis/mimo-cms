mimo名字来自mini & more的简写。
正如名字的来源，我希望mimo能有极少的代码实现足够多的功能。
当然，足够这个词的含义，仁者见仁智者见智。

对比之前写过的类cms的系统，首先mimo的源代码上少了很多。
砍掉了一些我认为不应该有的功能，也砍掉了一些我自认为写得比较绕的代码。
对我而言，是一个进步。

mimo大致用了以下几个框架：
spring，mybatis，shiro
在安全方面，我扔掉了spring security。
spring security是个强大的东西。
这个我承认，我真的承认。
但强大不意味着适合。
而强大在某种程度上意味着你要面面俱到，稍微不小心，就会成为一个鸡肋。
spring security体系复杂，学习曲线相当高，这是一个不容小视的开发成本。
而shiro对比之下，远比spring security清爽的多。
orm上，也是扔掉了hibernate。
hibernate在orm上做得相当不错。
在开发的过程中，很多时候，我们能保持无绪或者无知的状态。
要干什么？该干什么？交给hibernate就行了。
但有一天你想有绪有知起来，那就麻烦了。
hibernate提供了HQL的查询。
看起来挺不错的，可以减少一些sql的编写，也让sql这种东西更靠OOP，具有更好的可读性。
但它仍旧不能把面向数据库的东西从代码中脱离出来。
这也是一个鸡肋。
其次，一用hibernate，就得导那个包这个包，这让我很不舒服。
而mybatis正好帮我解决了这些心结。

spring mvc对比与struts2更易用，也更安全。
spring mvc中的controller跟servlet一样，都是stateless。
这会减少一些开销。
而struts2的set,get也会带来一些安全性的问题，鸡肋。
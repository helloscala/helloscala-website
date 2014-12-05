--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.3
-- Started on 2014-11-24 10:57:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE helloscala;
--
-- TOC entry 2685 (class 1262 OID 17034)
-- Name: helloscala; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE helloscala WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'zh_CN.UTF-8' LC_CTYPE = 'zh_CN.UTF-8';


\connect helloscala

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 2686 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 181 (class 3079 OID 11791)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2687 (class 0 OID 0)
-- Dependencies: 181
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 180 (class 3079 OID 17035)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2688 (class 0 OID 0)
-- Dependencies: 180
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 187 (class 3079 OID 17044)
-- Name: btree_gin; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS btree_gin WITH SCHEMA public;


--
-- TOC entry 2689 (class 0 OID 0)
-- Dependencies: 187
-- Name: EXTENSION btree_gin; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION btree_gin IS 'support for indexing common datatypes in GIN';


--
-- TOC entry 186 (class 3079 OID 17389)
-- Name: btree_gist; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS btree_gist WITH SCHEMA public;


--
-- TOC entry 2690 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION btree_gist; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION btree_gist IS 'support for indexing common datatypes in GiST';


--
-- TOC entry 185 (class 3079 OID 17911)
-- Name: dblink; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS dblink WITH SCHEMA public;


--
-- TOC entry 2691 (class 0 OID 0)
-- Dependencies: 185
-- Name: EXTENSION dblink; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION dblink IS 'connect to other PostgreSQL databases from within a database';


--
-- TOC entry 184 (class 3079 OID 17957)
-- Name: hstore; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA public;


--
-- TOC entry 2692 (class 0 OID 0)
-- Dependencies: 184
-- Name: EXTENSION hstore; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION hstore IS 'data type for storing sets of (key, value) pairs';


--
-- TOC entry 183 (class 3079 OID 18077)
-- Name: ltree; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS ltree WITH SCHEMA public;


--
-- TOC entry 2693 (class 0 OID 0)
-- Dependencies: 183
-- Name: EXTENSION ltree; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION ltree IS 'data type for hierarchical tree-like structures';


--
-- TOC entry 182 (class 3079 OID 18252)
-- Name: xml2; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS xml2 WITH SCHEMA public;


--
-- TOC entry 2694 (class 0 OID 0)
-- Dependencies: 182
-- Name: EXTENSION xml2; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION xml2 IS 'XPath querying and XSLT';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 18266)
-- Name: document_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE document_ (
    author character varying(1024) NOT NULL,
    description character varying(1024),
    comment_count bigint NOT NULL,
    slug character varying(1024),
    id character(24) NOT NULL,
    content text NOT NULL,
    created_at timestamp without time zone NOT NULL,
    title character varying(1024) NOT NULL
);


--
-- TOC entry 172 (class 1259 OID 18272)
-- Name: document_comment_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE document_comment_ (
    document_id character(24) NOT NULL,
    creator character varying(1024) NOT NULL,
    id character varying(1024) NOT NULL,
    content character varying(1024) NOT NULL,
    created_at timestamp without time zone NOT NULL
);


--
-- TOC entry 173 (class 1259 OID 18278)
-- Name: micro_chat_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE micro_chat_ (
    creator character varying(1024) NOT NULL,
    id character(24) NOT NULL,
    content text NOT NULL,
    created_at timestamp without time zone NOT NULL
);


--
-- TOC entry 174 (class 1259 OID 18284)
-- Name: micro_chat_reply_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE micro_chat_reply_ (
    creator character varying(1024) NOT NULL,
    id character(24) NOT NULL,
    content character varying(1024) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    micro_chat_id character varying(1024) NOT NULL
);


--
-- TOC entry 175 (class 1259 OID 18290)
-- Name: role_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE role_ (
    id character varying(1024) NOT NULL,
    comment character varying(1024),
    created_at timestamp without time zone NOT NULL
);


--
-- TOC entry 176 (class 1259 OID 18296)
-- Name: tag_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE tag_ (
    tag character varying(1024) NOT NULL,
    code integer NOT NULL,
    ref_id character(24) NOT NULL
);


--
-- TOC entry 177 (class 1259 OID 18302)
-- Name: user_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_ (
    nick character varying(1024),
    email character varying(1024) NOT NULL,
    signature character varying(1024),
    sex integer,
    id character varying(1024) NOT NULL,
    safe_email character varying(1024),
    created_at timestamp without time zone NOT NULL
);


--
-- TOC entry 178 (class 1259 OID 18308)
-- Name: user_password_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_password_ (
    id character varying(1024) NOT NULL,
    salt character varying(1024) NOT NULL,
    password character varying(1024) NOT NULL
);


--
-- TOC entry 179 (class 1259 OID 18314)
-- Name: user_role_; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_role_ (
    role_id character varying(1024) NOT NULL,
    user_id character varying(1024) NOT NULL
);


--
-- TOC entry 2672 (class 0 OID 18266)
-- Dependencies: 171
-- Data for Name: document_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO document_ (author, description, comment_count, slug, id, content, created_at, title) VALUES ('yangbajing', NULL, 0, NULL, '52773fece4b08945c1fb2807', '在Mac系统中，GUI程序并不会像Linux那样继承命令行设置的环境变量。若将在GUI程序中访问自定义环境变量，比如Intellij idea中。需要使用如下命令：

    $ launchctl setenv XXXXX /tmp/xxx

需要在系统重启和仍然生效，可把设置写入配置文件中`/etc/launched.conf`：

    setenv XXXXX /tmp/xxx', '2013-11-04 14:34:20.384', 'Mac系统环境变量设置');
INSERT INTO document_ (author, description, comment_count, slug, id, content, created_at, title) VALUES ('xiaoma', NULL, 1, NULL, '5277478ce4b08945c1fb2aca', '#第一章 什么是函数编程

"函数编程"是一种"编程范式"（programming paradigm），也就是如何编写程序的方法论。它基于这样一个简单的限制条件：只用“纯函数”编写程序，换句话说，就是只用没有“副作用”的函数编程。

通常情况下，以下一些操作都将会带来副作用:

* 修改变量
* 修改数据结构
* 设置对象的属性
* 捕获异常
* 控制台输入或者输出
* 读写一个文件
* 往显示屏上输出

假设一个程序不能做以上任何事情，我们难以想象，这个程序还能有什么用处。如果不能修改变量，那么如何写循环语句呢？函数编程就是告诉我们如何写出不带副作用的程序。事实证明，增加这种限制是有很多好处的，我们编写的程序更加模块化，纯函数的程序更容易测试，重用，并行化。为了获得这些好处，我们必须转变编程方式。

一个函数有一个类型A的输入和B类型的输出（在Scala中表示为A => B），对于每个指定的类型A的值a，都有唯一一个对应的B类型的值b。

例如，intToString就是一个Int => String的函数，对于每个指定的Int类型的数，都输出一个String类型的值。除此之外，它不做任何事情。换句话说，就是没有可以察觉到的其他任何计算返回值以外的操作，我们称这个函数为“纯函数”。其他的一些纯函数比如“+”，对于任何给定的两个整数，每次执行都会返回相同的一个整数。Java或者scala中String类型的length也是如此，其他还有很多这样的纯函数。

纯函数可以用表达式取代另外一个表达式中的值，例如，x=3+5，那么y=x+1 和 y=(3+5)+1 是完全等价的。非纯函数就不是这样了，下面用两个例子说明它们之间的区别。

	scala> val x = "Hello, World"
	x: java.lang.String = Hello, World
	scala> val r1 = x.reverse
	r1: String = dlroW ,olleH
	scala> val r2 = x.reverse
	r2: String = dlroW ,olleH

	scala> val r1 = "Hello, World".reverse
	r1: String = dlroW ,olleH
	val r2 = "Hello, World".reverse
	r2: String = dlroW ,olleH

在这个例子中，reverse是一个纯函数。reverse不会有副作用。

	scala> val x = new StringBuilder("Hello")
	x: java.lang.StringBuilder = Hello
	scala> val y = x.append(", World")
	y: java.lang.StringBuilder = Hello, World
	scala> val r1 = y.toString
	r1: java.lang.String = Hello, World
	scala> val r2 = y.toString
	r2: java.lang.String = Hello, World

	scala> val x = new StringBuilder("Hello")
	x: java.lang.StringBuilder = Hello
	scala> val r1 = x.append(", World").toString
	r1: java.lang.String = Hello, World
	scala> val r2 = x.append(", World").toString
	r2: java.lang.String = Hello, World, World

而在这里例子中，append除了返回以外，还改变了x的值，带来了副作用，因此它不是一个纯函数。

增加了这样限制的函数编程，给我们带来了很大程度的模块化。模块化程序是可以理解的和可以重复使用的组件，它独立于整体。这样，整体的含义仅仅依赖于它的组件和它们之间组合的规则的含义。模块可以看成是一个黑盒子，使得逻辑计算具有可重用性。', '2013-11-04 15:06:52.625', 'Scala函数编程读书笔记（一）');
INSERT INTO document_ (author, description, comment_count, slug, id, content, created_at, title) VALUES ('xumengyang', NULL, 1, '梦阳', '529015cfe4b09ece68124798', '很赞
--------

随便在google scala的group里面看了下，发现了这个地方，没什么人，呵呵，不过的确非常赞', '2013-11-23 10:41:19.579', '很赞的地方');
INSERT INTO document_ (author, description, comment_count, slug, id, content, created_at, title) VALUES ('yangbajing', '编写更好的技术文档', 2, 'mac-sphinx-pdf-zh_CN', '52773e75e4b08945c1fb2528', '# Mac 10.8 下安装Sphinx并支持生成中文PDF

最近一直在用Sphinx撰写文档，但是生成中文PDF时老是失败。今天在网上查了些资料，终于把它弄成功了。现记录如下。

需要用到的软件有：

* python 2.7
* Sphinx 1.2
* MacTex 2013

## 安装Sphinx

    $ sudo easy_install-2.7 Sphinx

## 安装MacTex

请到 `http://www.tug.org/mactex/` 下载，或 [点此](http://mirror.ctan.org/systems/mac/mactex/MacTeX.pkg) 下载。安装过程就略了。

## 让Sphinx latex支持中文

首先使用 `sphinx-quickstart` 生成Sphinx项目。然后修改 `conf.py` 文件。将如下段：

    latex_elements = {
    # The paper size (''letterpaper'' or ''a4paper'').
    #''papersize'': ''letterpaper'',
    
    # The font size (''10pt'', ''11pt'' or ''12pt'').
    #''pointsize'': ''10pt'',
    
    # Additional stuff for the LaTeX preamble.
    #''preamble'': '''',
    }

替换成：

    latex_elements = {
    # The paper size (''letterpaper'' or ''a4paper'').
    ''papersize'': ''a4paper'',
    
    # The font size (''10pt'', ''11pt'' or ''12pt'').
    #''pointsize'': ''12pt'',
    
    ''classoptions'': '',english'',
    ''inputenc'': '''',
    ''utf8extra'': '''',
    
    # Additional stuff for the LaTeX preamble.
    ''preamble'': ''''''
    \usepackage{xeCJK}
    \usepackage{indentfirst}
    \setlength{\parindent}{2em}
    \setCJKmainfont[BoldFont=SimHei, ItalicFont=STKaiti]{SimSun}
    \setCJKmonofont[Scale=0.9]{Consolas}
    \setCJKfamilyfont{song}[BoldFont=SimSun]{SimSun}
    \setCJKfamilyfont{sf}[BoldFont=SimSun]{SimSun}
    ''''''
    }

这些配置的具体含意我也不大清楚，不过自已修改下字体还是可行的。你可以使用 `fc-list :lang=zh-cn` 查看系统所中文字体名字。Mac默认没有此 `fc-list` 程序，可以使用brew安装。

    $ brew install fontconfig

## 生成PDF

首先你需要在Sphinx项目目录执行 `make latex` 命令生成latex，再使用 `xelatex *.tex` 生成PDF文件。具体步骤如下：

    $ make latex
    $ cd build/latex
    $ xelatex *.tex
    $ open *.pdf

## 结束

好了，现在享受Sphinx撰写文档的愉快心情吧！', '2013-11-04 14:28:05.757', 'Mac 10.8 下安装Sphinx并支持生成中文PDF');
INSERT INTO document_ (author, description, comment_count, slug, id, content, created_at, title) VALUES ('oldpig', 'scalaz版', 0, NULL, '52a188e3e4b0b514da816d89', '    import scala.annotation.tailrec
    import scalaz._
    import Ordering._

    val data = Vector(1, 2, 3, 4, 6, 9)

    def binary_search[T: Order](x: T, d: Vector[T]) = {
      @tailrec
      def helper(min: Int, max: Int): Int = {
        if (max <= min) -(min + 1)
        else {
          val middle = (min + max) / 2
          implicitly[Order[T]].apply(x, d(middle)) match {
            case EQ => middle
            case GT => helper(min + 1, max)
            case LT => helper(min, max - 1)
          }
        }
      }
      helper(0, d.size - 1)
    }

    assert(binary_search(3, data) == 2)
    assert(binary_search(5, data) == -5)


', '2013-12-06 16:20:51.972', '二分查找算法');


--
-- TOC entry 2673 (class 0 OID 18272)
-- Dependencies: 172
-- Data for Name: document_comment_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO document_comment_ (document_id, creator, id, content, created_at) VALUES ('5277478ce4b08945c1fb2aca', 'yangbajing', '52791776e4b08945c1fb3a90', '好文', '2013-11-06 00:06:14.542');
INSERT INTO document_comment_ (document_id, creator, id, content, created_at) VALUES ('52773e75e4b08945c1fb2528', 'yangbajing', '52831d82e4b056fe40a361b3', '自己顶！', '2013-11-13 14:34:42.218');
INSERT INTO document_comment_ (document_id, creator, id, content, created_at) VALUES ('52773e75e4b08945c1fb2528', 'yangbajing', '52870e86e4b0b4b2a6605bf4', '# 再顶！', '2013-11-16 14:19:50.597');
INSERT INTO document_comment_ (document_id, creator, id, content, created_at) VALUES ('529015cfe4b09ece68124798', 'yangbajing', '52922a94e4b09ece68124fb8', '哎，就是人气不高啊……', '2013-11-25 00:34:28.527');


--
-- TOC entry 2674 (class 0 OID 18278)
-- Dependencies: 173
-- Data for Name: micro_chat_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('xiaoma', '527726a7e4b0b7031cf8503b', 'hi', '2013-11-04 12:46:31.292');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('liango', '52778715e4b08945c1fb3067', 'hi', '2013-11-04 19:37:57.673');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('Eugene', '527a6edfe4b08945c1fb41c8', '', '2013-11-07 00:31:27.916');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('yangbajing', '52831dabe4b056fe40a361e1', '哈哈哈', '2013-11-13 14:35:23.491');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('law', '5284aaeee4b056fe40a37f93', '支持下', '2013-11-14 18:50:22.121');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('liango', '528709c6e4b0bc626d469673', '快使用scala，霍霍哈嗨', '2013-11-16 13:59:34.275');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('yangbajing', '5288e3c0e4b0b4b2a6607d2e', '中华人民共和国重庆Scala用户爱好者在此留言！！！！！', '2013-11-17 23:41:52.185');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('xumengyang', '529015f9e4b09ece681247e2', '支持一下', '2013-11-23 10:42:01.335');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('evan', '52a168c7e4b0b514da816a57', 'test', '2013-12-06 14:03:51.266');
INSERT INTO micro_chat_ (creator, id, content, created_at) VALUES ('kizzyang', '52c4cad4e4b09ee3dfcfd374', 'hello，羊哥', '2014-01-02 10:11:32.974');


--
-- TOC entry 2675 (class 0 OID 18284)
-- Dependencies: 174
-- Data for Name: micro_chat_reply_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO micro_chat_reply_ (creator, id, content, created_at, micro_chat_id) VALUES ('yangbajing', '52a2be89e4b0f49e1d41355b', '测试回复功能！', '2013-12-07 14:22:01.855', '52a168c7e4b0b514da816a57');


--
-- TOC entry 2676 (class 0 OID 18290)
-- Dependencies: 175
-- Data for Name: role_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO role_ (id, comment, created_at) VALUES ('root', NULL, '2013-12-07 01:50:39.809');
INSERT INTO role_ (id, comment, created_at) VALUES ('admin', NULL, '2013-12-07 01:50:39.809');
INSERT INTO role_ (id, comment, created_at) VALUES ('user', NULL, '2013-12-07 01:50:39.809');
INSERT INTO role_ (id, comment, created_at) VALUES ('guest', NULL, '2013-12-07 01:50:39.809');


--
-- TOC entry 2677 (class 0 OID 18296)
-- Dependencies: 176
-- Data for Name: tag_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tag_ (tag, code, ref_id) VALUES ('scala', 1, '52769529e4b0b79b6523661c');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('liftweb', 1, '52769529e4b0b79b6523661c');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('postgresql', 1, '52769529e4b0b79b6523661c');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('mac', 1, '52773e75e4b08945c1fb2528');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('sphinx', 1, '52773e75e4b08945c1fb2528');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('pdf', 1, '52773e75e4b08945c1fb2528');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('中文', 1, '52773e75e4b08945c1fb2528');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('mac', 1, '52773fece4b08945c1fb2807');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('system', 1, '52773fece4b08945c1fb2807');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('environment', 1, '52773fece4b08945c1fb2807');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('launchctl', 1, '52773fece4b08945c1fb2807');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('setenv', 1, '52773fece4b08945c1fb2807');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('scala', 1, '5277478ce4b08945c1fb2aca');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('闲聊', 1, '529015cfe4b09ece68124798');
INSERT INTO tag_ (tag, code, ref_id) VALUES ('scalaz', 1, '52a188e3e4b0b514da816d89');


--
-- TOC entry 2678 (class 0 OID 18302)
-- Dependencies: 177
-- Data for Name: user_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'laogaome@qq.com', NULL, NULL, 'laogao', NULL, '2013-10-22 10:47:48.663');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'zsy.evan@gmail.com', NULL, NULL, 'evan', NULL, '2013-10-22 17:43:53.288');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES ('小马', 'cnxiaoma@gmail.com', NULL, 1, 'xiaoma', NULL, '2013-11-04 12:46:18.856');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'fuxieji@gmail.com', NULL, NULL, 'tldeti', NULL, '2013-11-04 14:43:21.626');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '540625404@qq.com', NULL, NULL, 'liango', NULL, '2013-11-04 19:37:31.918');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '1251316150@qq.com', NULL, NULL, 'law', NULL, '2013-11-14 18:49:59.7');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'eugene.c@outlook.com', NULL, NULL, 'eugene', NULL, '2013-11-07 00:31:08.373');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '153034815@qq.com', NULL, NULL, 'leo', NULL, '2013-11-04 14:33:12.092');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'whxumengyang@gmail.com', NULL, NULL, 'xumengyang', NULL, '2013-11-23 10:38:57.924');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '519870018@qq.com', NULL, NULL, 'kizzyang', NULL, '2013-11-26 21:55:52.456');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '923933533@qq.com', NULL, NULL, 'liujiuwu', NULL, '2013-12-05 19:11:51.022');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES ('老猪', '43284683@qq.com', NULL, 1, 'oldpig', NULL, '2013-12-06 16:16:37.401');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES ('羊八井', 'yangbajing@gmail.com', NULL, 1, 'yangbajing', 'yangbajing@gmail.com', '2013-10-20 20:47:51.488');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, '514400413@qq.com', NULL, NULL, 'w514400413', NULL, '2013-12-22 16:49:50.747');
INSERT INTO user_ (nick, email, signature, sex, id, safe_email, created_at) VALUES (NULL, 'alexdotnet@163.com', NULL, NULL, 'steve', NULL, '2013-12-27 10:14:34.46');


--
-- TOC entry 2679 (class 0 OID 18308)
-- Dependencies: 178
-- Data for Name: user_password_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_password_ (id, salt, password) VALUES ('laogao', '13TTOSPM', 'dc7cda49fafbdc351549889d07fbb5d6a2403152');
INSERT INTO user_password_ (id, salt, password) VALUES ('evan', 'HEB2HMLI', '2033a2e728f4bb5dad8605d880e87c74aaaaa5a9');
INSERT INTO user_password_ (id, salt, password) VALUES ('xiaoma', 'EOSTSNC1', '580cf528a3d7855337d8bbe30e8390ab88bfaecc');
INSERT INTO user_password_ (id, salt, password) VALUES ('Leo', 'GX1TRDHE', 'ab0f386413b3e332644be9dfd7b42b956cc12de6');
INSERT INTO user_password_ (id, salt, password) VALUES ('tldeti', 'FHI2DCQK', '60d045846c3ab02397c16e64bfd258d52efbe98a');
INSERT INTO user_password_ (id, salt, password) VALUES ('liango', 'TSSLEJ0T', 'a3af41548ffcda6560cc3ab4dbe33e1a73cafb8c');
INSERT INTO user_password_ (id, salt, password) VALUES ('eugene', 'NO5RAYKR', '02354d2a86f668a0bd5f105ce80d4e53153f69f4');
INSERT INTO user_password_ (id, salt, password) VALUES ('yangbajing', 'T1XCS01H', 'bad02cbdc16aecae79cc1b56abf539fc27e87799');
INSERT INTO user_password_ (id, salt, password) VALUES ('law', '12D20HFA', 'f0950d71ffa7da68937aedb001f114990f5333f9');
INSERT INTO user_password_ (id, salt, password) VALUES ('xumengyang', 'S4ICNHTR', '7fc9457b3097670060e2a9fd9d674335f2860fa3');
INSERT INTO user_password_ (id, salt, password) VALUES ('kizzyang', 'V2TLF1B4', '9b1bf2d3a9f78fa189a03c3e0ff1cb4f4b3d0195');
INSERT INTO user_password_ (id, salt, password) VALUES ('liujiuwu', 'Z0HU3ALX', '1acfec485db91baed9ee095488cbf6bed0b5ff74');
INSERT INTO user_password_ (id, salt, password) VALUES ('oldpig', 'YOISCDYV', '127bcaf9826431b6aeaa0d75bd78b431d9940f06');
INSERT INTO user_password_ (id, salt, password) VALUES ('w514400413', 'KOJLSGVY', 'd473c666816025e8d00e335e9e504b4fd845c25c');
INSERT INTO user_password_ (id, salt, password) VALUES ('steve', 'AJQ0O0DB', 'd67fe5c029f84a02d09bc41a23e3350dde029127');


--
-- TOC entry 2680 (class 0 OID 18314)
-- Dependencies: 179
-- Data for Name: user_role_; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_role_ (role_id, user_id) VALUES ('admin', 'yangbajing');


--
-- TOC entry 2540 (class 2606 OID 18321)
-- Name: document__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY document_
    ADD CONSTRAINT document__pkey PRIMARY KEY (id);


--
-- TOC entry 2544 (class 2606 OID 18323)
-- Name: document_comment__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY document_comment_
    ADD CONSTRAINT document_comment__pkey PRIMARY KEY (id);


--
-- TOC entry 2548 (class 2606 OID 18325)
-- Name: micro_chat__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY micro_chat_
    ADD CONSTRAINT micro_chat__pkey PRIMARY KEY (id);


--
-- TOC entry 2551 (class 2606 OID 18327)
-- Name: micro_chat_reply__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY micro_chat_reply_
    ADD CONSTRAINT micro_chat_reply__pkey PRIMARY KEY (id);


--
-- TOC entry 2553 (class 2606 OID 18329)
-- Name: role__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role_
    ADD CONSTRAINT role__pkey PRIMARY KEY (id);


--
-- TOC entry 2556 (class 2606 OID 18331)
-- Name: tag__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tag_
    ADD CONSTRAINT tag__pkey PRIMARY KEY (ref_id, tag);


--
-- TOC entry 2558 (class 2606 OID 18333)
-- Name: user__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (id);


--
-- TOC entry 2562 (class 2606 OID 18335)
-- Name: user_password__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_password_
    ADD CONSTRAINT user_password__pkey PRIMARY KEY (id);


--
-- TOC entry 2564 (class 2606 OID 18337)
-- Name: user_role__pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_
    ADD CONSTRAINT user_role__pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2545 (class 1259 OID 18338)
-- Name: document_comment_created_at__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX document_comment_created_at__idx ON document_comment_ USING btree (created_at);


--
-- TOC entry 2541 (class 1259 OID 18339)
-- Name: document_created_at__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX document_created_at__idx ON document_ USING btree (created_at);


--
-- TOC entry 2542 (class 1259 OID 18340)
-- Name: document_slug__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX document_slug__idx ON document_ USING btree (slug);


--
-- TOC entry 2546 (class 1259 OID 18341)
-- Name: micro_char__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX micro_char__idx ON micro_chat_ USING btree (created_at);


--
-- TOC entry 2549 (class 1259 OID 18342)
-- Name: micro_chart_reply_created_at__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX micro_chart_reply_created_at__idx ON micro_chat_reply_ USING btree (created_at);


--
-- TOC entry 2554 (class 1259 OID 18343)
-- Name: role_created_at__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX role_created_at__idx ON role_ USING btree (created_at);


--
-- TOC entry 2559 (class 1259 OID 18344)
-- Name: user_created_at__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX user_created_at__idx ON user_ USING btree (created_at);


--
-- TOC entry 2560 (class 1259 OID 18345)
-- Name: user_email__idx; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX user_email__idx ON user_ USING btree (email);


-- Completed on 2014-11-24 10:57:47

--
-- PostgreSQL database dump complete
--


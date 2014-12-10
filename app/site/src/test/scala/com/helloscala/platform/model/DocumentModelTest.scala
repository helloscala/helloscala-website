package com.helloscala.platform.model

import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.common.SuperId
import com.helloscala.platform.model.entity.MDocument
import com.helloscala.platform.util.Y
import org.scalatest.FunSpec

class DocumentModelTest extends FunSpec with MyFunSpec {
  val documentModel = DocumentModel(entities)

  describe("DocumentModel") {
    it("import document") {
      val title = "很赞的地方"
      val author = 9L
      val description = None
      val slug = None
      val content = """很赞
                      |--------
                      |
                      |随便在google scala的group里面看了下，发现了这个地方，没什么人，呵呵，不过的确非常赞""".stripMargin
      val createdAt = Y.formatDateTimeMillis.parseDateTime("2013-11-23 10:41:19.579")
      val document =
        documentModel.insert(MDocument(None, title, author, content, description, slug, created_at = createdAt), Nil)
      println(document)
    }

    it("update document") {
      val document = documentModel.findOneById(SuperId("5472d1c95de39af20db12204")).get
      val content =
        """# Mac 10.8 下安装Sphinx并支持生成中文PDF
          |
          |最近一直在用Sphinx撰写文档，但是生成中文PDF时老是失败。今天在网上查了些资料，终于把它弄成功了。现记录如下。
          |
          |需要用到的软件有：
          |
          |* python 2.7
          |* Sphinx 1.2
          |* MacTex 2013
          |
          |## 安装Sphinx
          |
          |    $ sudo easy_install-2.7 Sphinx
          |
          |## 安装MacTex
          |
          |请到 `http://www.tug.org/mactex/` 下载，或 [点此](http://mirror.ctan.org/systems/mac/mactex/MacTeX.pkg) 下载。安装过程就略了。
          |
          |## 让Sphinx latex支持中文
          |
          |首先使用 `sphinx-quickstart` 生成Sphinx项目。然后修改 `conf.py` 文件。将如下段：
          |
          |    latex_elements = {
          |    # The paper size (''letterpaper'' or ''a4paper'').
          |    #''papersize'': ''letterpaper'',
          |
          |    # The font size (''10pt'', ''11pt'' or ''12pt'').
          |    #''pointsize'': ''10pt'',
          |
          |    # Additional stuff for the LaTeX preamble.
          |    #''preamble'': '''',
          |    }
          |
          |替换成：
          |
          |    latex_elements = {
          |    # The paper size (''letterpaper'' or ''a4paper'').
          |    ''papersize'': ''a4paper'',
          |
          |    # The font size (''10pt'', ''11pt'' or ''12pt'').
          |    #''pointsize'': ''12pt'',
          |
          |    ''classoptions'': '',english'',
          |    ''inputenc'': '''',
          |    ''utf8extra'': '''',
          |
          |    # Additional stuff for the LaTeX preamble.
          |    ''preamble'': ''''''
          |    \\usepackage{xeCJK}
          |    \\usepackage{indentfirst}
          |    \setlength{\parindent}{2em}
          |    \setCJKmainfont[BoldFont=SimHei, ItalicFont=STKaiti]{SimSun}
          |    \setCJKmonofont[Scale=0.9]{Consolas}
          |    \setCJKfamilyfont{song}[BoldFont=SimSun]{SimSun}
          |    \setCJKfamilyfont{sf}[BoldFont=SimSun]{SimSun}
          |    ''''''
          |    }
          |
          |这些配置的具体含意我也不大清楚，不过自已修改下字体还是可行的。你可以使用 `fc-list :lang=zh-cn` 查看系统所中文字体名字。Mac默认没有此 `fc-list` 程序，可以使用brew安装。
          |
          |    $ brew install fontconfig
          |
          |## 生成PDF
          |
          |首先你需要在Sphinx项目目录执行 `make latex` 命令生成latex，再使用 `xelatex *.tex` 生成PDF文件。具体步骤如下：
          |
          |    $ make latex
          |    $ cd build/latex
          |    $ xelatex *.tex
          |    $ open *.pdf
          |
          |## 结束
          |
          |好了，现在享受Sphinx撰写文档的愉快心情吧！""".stripMargin
      val result = documentModel.update(document.copy(content = content))
      println(result)
    }
  }
}

/*
 * Vens Aliucord Plugins
 * Copyright (C) 2021 Vendicated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
*/

package dev.vendicated.aliucordplugins.textfilepreview

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aliucord.Logger
import com.aliucord.Utils
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.after
import com.aliucord.wrappers.messages.AttachmentWrapper.Companion.url
import com.discord.widgets.chat.list.adapter.WidgetChatListAdapterItemAttachment

val logger = Logger("TextFilePreview")

@AliucordPlugin
class TextFilePreview : Plugin() {
    init {
        settingsTab = SettingsTab(LeSettings::class.java)
    }
    
    override fun start(ctx: Context) {
        patcher.after<WidgetChatListAdapterItemAttachment>("configureUI", WidgetChatListAdapterItemAttachment.Model::class.java) { param ->
            val model = param.args[0] as WidgetChatListAdapterItemAttachment.Model
            val entry = model.attachmentEntry

            val layout = itemView as ConstraintLayout
            val cardView = layout.findViewById<CardView>(Utils.getResId("chat_list_item_attachment_card", "id"))
            cardView.findViewById<AttachmentPreviewWidget>(previewWidgetId)?.let {
                cardView.getChildAt(0).visibility = View.VISIBLE
                cardView.removeView(it)
            }

            if (plainTextExtensions.contains(entry.attachment.url.substringAfterLast('.'))) {
                cardView.getChildAt(0).visibility = View.GONE
                cardView.addView(AttachmentPreviewWidget(cardView.context, entry.attachment, settings))
                cardView.setOnClickListener(null)
            }
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}

// taken from desktop app
// require("powercord/webpack").getModule(["PLAINTEXT_FILE_EXTENSIONS"], false).PLAINTEXT_FILE_EXTENSIONS
val plainTextExtensions = arrayOf(
    "1c",
    "4d",
    "abnf",
    "accesslog",
    "ada",
    "arduino",
    "ino",
    "armasm",
    "arm",
    "avrasm",
    "actionscript",
    "as",
    "alan",
    "i",
    "log",
    "ln",
    "angelscript",
    "asc",
    "apache",
    "apacheconf",
    "applescript",
    "osascript",
    "arcade",
    "asciidoc",
    "adoc",
    "aspectj",
    "autohotkey",
    "autoit",
    "awk",
    "mawk",
    "nawk",
    "gawk",
    "bash",
    "sh",
    "zsh",
    "basic",
    "bbcode",
    "blade",
    "bnf",
    "brainfuck",
    "bf",
    "csharp",
    "cs",
    "c",
    "h",
    "cpp",
    "hpp",
    "cc",
    "hh",
    "c++",
    "h++",
    "cxx",
    "hxx",
    "cal",
    "cos",
    "cls",
    "cmake",
    "cmake.in",
    "coq",
    "csp",
    "css",
    "csv",
    "capnproto",
    "capnp",
    "chaos",
    "kaos",
    "chapel",
    "chpl",
    "cisco",
    "clojure",
    "clj",
    "coffeescript",
    "coffee",
    "cson",
    "iced",
    "cpc",
    "crmsh",
    "crm",
    "pcmk",
    "crystal",
    "cr",
    "cypher",
    "d",
    "dns",
    "zone",
    "bind",
    "dos",
    "bat",
    "cmd",
    "dart",
    "delphi",
    "dpr",
    "dfm",
    "pas",
    "pascal",
    "freepascal",
    "lazarus",
    "lpr",
    "lfm",
    "diff",
    "patch",
    "django",
    "jinja",
    "dockerfile",
    "docker",
    "dsconfig",
    "dts",
    "dust",
    "dst",
    "dylan",
    "ebnf",
    "elixir",
    "ex",
    "elm",
    "erlang",
    "erl",
    "extempore",
    "xtlang",
    "xtm",
    "fsharp",
    "fs",
    "fix",
    "fortran",
    "f90",
    "f95",
    "gcode",
    "nc",
    "gams",
    "gms",
    "gauss",
    "gss",
    "godot",
    "gdscript",
    "gherkin",
    "gn",
    "gni",
    "go",
    "golang",
    "gf",
    "golo",
    "gololang",
    "gradle",
    "groovy",
    "xml",
    "html",
    "xhtml",
    "rss",
    "atom",
    "xjb",
    "xsd",
    "xsl",
    "plist",
    "svg",
    "http",
    "https",
    "haml",
    "handlebars",
    "hbs",
    "html.hbs",
    "html.handlebars",
    "haskell",
    "hs",
    "haxe",
    "hx",
    "hy",
    "hylang",
    "ini",
    "toml",
    "inform7",
    "i7",
    "irpf90",
    "json",
    "java",
    "jsp",
    "javascript",
    "js",
    "jsx",
    "jolie",
    "iol",
    "ol",
    "julia",
    "julia-repl",
    "kotlin",
    "kt",
    "tex",
    "leaf",
    "lean",
    "lasso",
    "ls",
    "lassoscript",
    "less",
    "ldif",
    "lisp",
    "livecodeserver",
    "livescript",
    "lock",
    "lua",
    "makefile",
    "mk",
    "mak",
    "make",
    "markdown",
    "md",
    "mkdown",
    "mkd",
    "mathematica",
    "mma",
    "wl",
    "matlab",
    "maxima",
    "mel",
    "mercury",
    "mirc",
    "mrc",
    "mizar",
    "mojolicious",
    "monkey",
    "moonscript",
    "moon",
    "n1ql",
    "nsis",
    "never",
    "nginx",
    "nginxconf",
    "nim",
    "nimrod",
    "nix",
    "ocl",
    "ocaml",
    "ml",
    "objectivec",
    "mm",
    "objc",
    "obj-c",
    "obj-c++",
    "objective-c++",
    "glsl",
    "openscad",
    "scad",
    "ruleslanguage",
    "oxygene",
    "pf",
    "pf.conf",
    "php",
    "php3",
    "php4",
    "php5",
    "php6",
    "php7",
    "parser3",
    "perl",
    "pl",
    "pm",
    "plaintext",
    "txt",
    "text",
    "pony",
    "pgsql",
    "postgres",
    "postgresql",
    "powershell",
    "ps",
    "ps1",
    "processing",
    "prolog",
    "properties",
    "protobuf",
    "puppet",
    "pp",
    "python",
    "py",
    "gyp",
    "profile",
    "python-repl",
    "pycon",
    "k",
    "kdb",
    "qml",
    "r",
    "cshtml",
    "razor",
    "razor-cshtml",
    "reasonml",
    "re",
    "redbol",
    "rebol",
    "red",
    "red-system",
    "rib",
    "rsl",
    "graph",
    "instances",
    "robot",
    "rf",
    "rpm-specfile",
    "rpm",
    "spec",
    "rpm-spec",
    "specfile",
    "ruby",
    "rb",
    "gemspec",
    "podspec",
    "thor",
    "irb",
    "rust",
    "rs",
    "SAS",
    "sas",
    "scss",
    "sql",
    "p21",
    "step",
    "stp",
    "scala",
    "scheme",
    "scilab",
    "sci",
    "shexc",
    "shell",
    "console",
    "smali",
    "smalltalk",
    "st",
    "sml",
    "solidity",
    "sol",
    "stan",
    "stanfuncs",
    "stata",
    "iecst",
    "scl",
    "stl",
    "structured-text",
    "stylus",
    "styl",
    "subunit",
    "supercollider",
    "sc",
    "svelte",
    "swift",
    "tcl",
    "tk",
    "terraform",
    "tf",
    "hcl",
    "tap",
    "thrift",
    "tp",
    "tsql",
    "twig",
    "craftcms",
    "typescript",
    "ts",
    "tsx",
    "unicorn-rails-log",
    "vbnet",
    "vb",
    "vba",
    "vbscript",
    "vbs",
    "vhdl",
    "vala",
    "verilog",
    "v",
    "vim",
    "axapta",
    "x++",
    "x86asm",
    "xl",
    "tao",
    "xquery",
    "xpath",
    "xq",
    "yml",
    "yaml",
    "zephir",
    "zep"
)

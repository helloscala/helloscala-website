package com.helloscala.site.beans

case class DocumentBean(id: String,
                        title: String,
                        author: Long,
                        content: String,
                        description: String,
                        slug: String,

                        allow_anonymous: Boolean,

                        /**
                         * @see MTag
                         */
                        tags: java.util.List[Long],

                        /**
                         * username: "缓存用户名",
                         * nick: "缓存用户昵称"
                         * tag_{id}: "缓存Tag（多个Tag以tag_{id}键保存）",
                         */
                        attrs: java.util.Map[String, String],
                        created_at: java.util.Date)

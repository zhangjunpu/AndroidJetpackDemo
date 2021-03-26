# PixabayGallery

a simple gallery from pixabay.com



Todo

1. Filter
2. Search

## 分类

q
URL编码的搜索词，如果省略，则返回所有图片，不能超过100个字符。
例: "yellow+flower"

lang
要搜索的语言的语言代码
支持: cs, da, de, en, es, fr, id, it, hu, nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru, el, ja, ko, zh
默认: "en"

image_type
图片类型
支持: "all", "photo", "illustration", "vector"
默认: "all"

orientation
方向
支持: "all", "horizontal", "vertical"
默认: "all"

category
分类
支持: backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music

min_width
最小宽度
默认: "0"

min_height
最小高度
默认: "0"

colors
过滤图像的颜色属性，多个值用逗号分隔
支持: "grayscale", "transparent", "red", "orange", "yellow", "green", "turquoise", "blue", "lilac", "pink", "white", "gray", "black", "brown"

editors_choice
小编精选
支持: "true", "false"
默认: "false"

safesearch
只返回适合所有年龄的图像
支持: "true", "false"
默认: "false"

order
排序
支持: "popular", "latest"
默认: "popular"

page
页码
默认: 1

per_page
每页返回数量
支持: 3 - 200
默认: 20


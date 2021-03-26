package com.junpu.pixabaygallery.net

/**
 * 加载状态
 * @author junpu
 * @date 2020/7/31
 */
enum class LoadStatus(val msg: String) {
    LOADING_INITIAL("加载中"),
    LOADING("加载中"),
    SUCCESS("加载完成"),
    FAILURE("加载失败，点击重试"),
    COMPLETE("全部加载完成")
}

const val API_Q = "q" // URL编码的搜索词，如果省略，则返回所有图片，不能超过100个字符。
const val API_LANG = "lang" // 要搜索的语言的语言代码，默认: "en"
const val API_IMAGE_TYPE = "image_type" // 图片类型，"all"
const val API_ORIENTATION = "orientation" // 方向，"all"
const val API_CATEGORY = "category" // 分类
const val API_MIN_WIDTH = "min_width" // 最小宽度，默认: "0"
const val API_MIN_HEIGHT = "min_height" // 最小高度，默认: "0"
const val API_COLORS = "colors" // 过滤图像的颜色属性，多个值用逗号分隔
const val API_EDITORS_CHOICE = "editors_choice" // 小编精选，默认: "false"
const val API_SAFE_SEARCH = "safesearch" // 只返回适合所有年龄的图像，默认: "false"
const val API_ORDER = "order" // 排序
const val API_PAGE = "page" // 页码
const val API_PER_PAGE = "per_page" // 每页返回数量 3-200 ,默认0

/**
 * 语言
 * @author junpu
 * @date 2020/8/3
 */
enum class Lang(val lang: String) {
    CS("cs"),
    DA("da"),
    DE("de"),
    EN("en"),
    ES("es"),
    FR("fr"),
    ID("id"),
    IT("it"),
    HU("hu"),
    NL("nl"),
    NO("no"),
    PL("pl"),
    PT("pt"),
    RO("ro"),
    SK("sk"),
    FI("fi"),
    SV("sv"),
    TR("tr"),
    VI("vi"),
    TH("th"),
    BG("bg"),
    RU("ru"),
    EL("el"),
    JA("ja"),
    KO("ko"),
    ZH("zh")
}

/**
 * 分类
 * @author junpu
 * @date 2020/7/31
 */
enum class Category(val english: String, val chinese: String) {
    BACKGROUNDS("backgrounds", "背景"),
    FASHION("fashion", "时尚"),
    NATURE("nature", "自然"),
    SCIENCE("science", "科学"),
    EDUCATION("education", "教育"),
    FEELINGS("feelings", "情感"),
    HEALTH("health", "健康"),
    PEOPLE("people", "人"),
    RELIGION("religion", "宗教"),
    PLACES("places", "地方"),
    ANIMALS("animals", "动物"),
    INDUSTRY("industry", "行业"),
    COMPUTER("computer", "计算机"),
    FOOD("food", "食物"),
    SPORTS("sports", "运动"),
    TRANSPORTATION("transportation", "交通工具"),
    TRAVEL("travel", "旅行"),
    BUILDINGS("buildings", "建筑"),
    BUSINESS("business", "业务"),
    MUSIC("music", "音乐")
}

/**
 * 照片类型
 * @author junpu
 * @date 2020/7/31
 */
enum class ImageType(val english: String, val chinese: String) {
    ALL("all", "全部"),
    PHOTO("photo", "照片"),
    ILLUSTRATION("illustration", "插图"),
    VECTOR("vector", "矢量")
}

/**
 * 图片方向
 * @author junpu
 * @date 2020/7/31
 */
enum class ImageOrientation(val english: String, val chinese: String) {
    ALL("all", "全部"),
    HORIZONTAL("horizontal", "横向"),
    VERTICAL("vertical", "纵向")
}

/**
 * 图片颜色
 * @author junpu
 * @date 2020/7/31
 */
enum class ImageColor(val english: String, val chinese: String) {
    GRAYSCALE("grayscale", "灰度"),
    TRANSPARENT("transparent", "透明"),
    RED("red", "红色"),
    ORANGE("orange", "橙色"),
    YELLOW("yellow", "黄色"),
    GREEN("green", "绿色"),
    TURQUOISE("turquoise", "绿宝石"),
    BLUE("blue", "蓝色"),
    LILAC("lilac", "淡紫色"),
    PINK("pink", "粉红色"),
    WHITE("white", "白色"),
    GRAY("gray", "灰色"),
    BLACK("black", "黑色"),
    BROWN("brown", "棕色")
}

/**
 * 排序方式
 * @author junpu
 * @date 2020/8/3
 */
enum class Order(val english: String, val chinese: String) {
    POPULAR("popular", "流行"),
    LATEST("latest", "最新")
}
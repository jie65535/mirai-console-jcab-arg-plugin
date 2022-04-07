package top.jie65535

import Transmutation
import Silicon
import Silver
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info
import java.util.*

object JAlchemyRecipeGenerator : KotlinPlugin(
    JvmPluginDescription(
        id = "top.jie65535.mirai-console-jcab-arg-plugin",
        name = "Create: Above and Beyond 炼金配方生成器",
        version = "1.2.0",
    ) {
        author("jie65535")
        info("Create: Above and Beyond 整合包\n炼金配方生成器，输入种子生成配方")
    }
) {

    override fun onEnable() {
        GlobalEventChannel.parentScope(this).subscribeAlways<MessageEvent> {
            val msg = message.content
            if (msg.startsWith("chaos ")) {
                val seedStr = msg.removePrefix("chaos ").trim()
                var seed = seedStr.toLongOrNull()
                if (seed == null) {
                    seed = seedStr.hashCode().toLong()
                }
                subject.sendMessage(gen(seed))
            }
        }

        logger.info { "Plugin loaded" }
    }

    private fun gen(seed: Long): String {
        val random = Random(seed)
        val sb = StringBuilder()
        val transmutation = Transmutation(seed)
        for (catalyst in catalysts) {
            sb.append(catalyst.name).append(" : ")
            for (i in 1..4)
                sb.append(catalyst.materials[random.nextInt(catalyst.materials.size)]).append(' ')
            sb.appendLine()

            for (i in 1..5) {
                random.nextInt()
            }
//                val ret = IntArray(6) { it }
//                for (i in ret.size-1 downTo 1) {
//                    val j = random.nextInt(i+1)
//                    val temp = ret[i]
//                    ret[i] = ret[j]
//                    ret[j] = temp
//                }
//                sb.append('[')
//                for (i in ret) {
//                    sb.append(' ').append(i).append(',')
//                }
//                sb.appendLine(']')
        }
        // 硅
        val siliconReactant = transmutation.transmuted(Silicon).name
        sb.appendLine("硅反应物：混沌催化剂 + ${siliconReactant}反应物")
        sb.appendLine("银反应物：混沌催化剂 + ${transmutation.transmuted(Silver).name}反应物")

        val score = materialScore[siliconReactant] ?: 5
        sb.append("种子评价【")
        for (l in 1..5)
            sb.append(if (l <= score) "★" else "☆")
        sb.append("】\n评语：").append(scoreComment[score])

        return sb.toString()
    }

    private val catalysts = arrayOf(
        Catalyst("火成催化剂",   arrayOf("安山岩", "闪长岩", "花岗岩", "圆石", "玄武岩", "辉长岩")),
        Catalyst("草本催化剂",   arrayOf("绯红", "橙色", "黄色", "绿色", "蓝色", "品红色")),
        Catalyst("不稳定催化剂", arrayOf("烈焰", "史莱姆", "下界", "黑曜石", "火药", "海晶")),
        Catalyst("晶化催化剂",   arrayOf("神秘", "磷灰石", "硫磺", "硝石", "赛特斯石英", "下界石英")),
        Catalyst("金属催化剂",   arrayOf("锌", "铜", "铁", "镍", "铅", "金")),
        Catalyst("宝石催化剂",   arrayOf("朱砂", "青金石", "蓝宝石", "绿宝石", "红宝石", "钻石")),
        Catalyst("混沌催化剂",   arrayOf("火成催化剂", "草本催化剂", "不稳定催化剂", "晶化催化剂", "金属催化剂", "宝石催化剂")),
    )

    /**
     * 反应物材料价值表
     * https://docs.qq.com/sheet/DZndZSVFxSkdnaXh0
     */
    private val materialScore = mapOf(
        "安山岩" to 5,	"闪长岩" to 5,	"花岗岩" to 5,	"圆石" to 5,	"玄武岩" to 5,	"辉长岩" to 5,
        "绯红" to 5,	"橙色" to 5,	"黄色" to 5,	"绿色" to 5,	"蓝色" to 5,	"品红色" to 5,
        "烈焰" to 3,	"史莱姆" to 4,	"下界" to 5,	"黑曜石" to 5,	"火药" to 5,	"海晶" to 3,
        "神秘" to 1,	"磷灰石" to 4,	"硫磺" to 4,	"硝石" to 4,	"赛特斯石英" to 3,	"下界石英" to 3,
        "锌" to 4,	"铜" to 4,	"铁" to 4,	"镍" to 3,	"铅" to 3,	"金" to 2,
        "朱砂" to 3,	"青金石" to 3,	"蓝宝石" to 1,	"绿宝石" to 1,	"红宝石" to 1,	"钻石" to 1,
    )

    /**
     * 评分评语
     */
    private val scoreComment = mapOf(
        1 to "逆大天，这破种子赶紧remake吧",
        2 to "寄了",
        3 to "不大行",
        4 to "还不错",
        5 to "这运气没谁了，吃点好的吧",
    )

    /**
     * 反应物
     */
    val reagents = catalysts.filter { it.name != "混沌催化剂" }
        .flatMap { it.materials.asIterable() }
}

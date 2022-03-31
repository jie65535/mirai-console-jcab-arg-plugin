import java.util.*

/**
 * 反应物炼金
 */
class Transmutation(seed: Long) {
    private val chaosMap = IntArray(REAGENT_COUNT) { it }

    init {
        var swap: Int
        val ran = Random(seed)
        val arr = IntArray(REAGENT_COUNT) { it }
        // 根据 CAB 脚本代码消费几次 ran
        repeat(7) {
            repeat(4) { ran.nextInt(6) }
            shuffle(IntArray(6), ran)
        }
        // 反应物洗牌（混沌 step 1）
        shuffle(arr, ran)
        for (i in 0 until REAGENT_COUNT step 2) {
            if (arr[i] >= Silicon.index && arr[i + 1] >= Silicon.index) {
                if (i == 0) {
                    swap = arr[2]
                    arr[2] = arr[1]
                    arr[1] = swap
                } else {
                    swap = arr[i - 1]
                    arr[i - 1] = arr[i]
                    arr[i] = swap
                }
            }
        }
        // 混沌 step 2：置换
        for (x in 0 until REAGENT_COUNT step 2) {
            chaosMap[arr[x]] = arr[x + 1]
            chaosMap[arr[x + 1]] = arr[x]
        }
    }

    /**
     * 根据产出查询混沌配方素材
     */
    fun transmuted(result: Reagent) = Reagent(chaosMap[result.index])

    /**
     * 洗牌
     */
    private fun shuffle(arr: IntArray, ran: Random) {
        var tmp: Int
        for (i in arr.lastIndex downTo 1) {
            val j = ran.nextInt(i + 1)
            tmp = arr[i]
            arr[i] = arr[j]
            arr[j] = tmp
        }
    }
}

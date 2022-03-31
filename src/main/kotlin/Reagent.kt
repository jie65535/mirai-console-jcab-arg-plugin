import top.jie65535.JAlchemyRecipeGenerator.reagents

/**
 * 反应物种类量
 */
const val REAGENT_COUNT = 38

/**
 * 反应物
 */
open class Reagent {
    val index: Int
    val name: String

    constructor(index: Int) {
        this.index = index
        this.name = if (index !in 0 until REAGENT_COUNT)
            UndefineReagent.name
        else when (index) {
            Silicon.index -> Silicon.name
            Silver.index -> Silver.name
            else -> reagents[index]
        }
    }

    constructor(name: String) {
        this.name = name
        this.index = when (name) {
            Silver.name -> Silver.index
            Silicon.name -> Silicon.index
            else -> reagents.indexOf(name)
        }
    }

    constructor(name: String, index: Int) {
        this.name = name
        this.index = index
    }
}

/**
 * 未定义的反应物
 */
object UndefineReagent : Reagent("", -1)

/**
 * 硅反应物
 */
object Silicon : Reagent("硅", 36)

/**
 * 银反应物
 */
object Silver : Reagent("银", 37)

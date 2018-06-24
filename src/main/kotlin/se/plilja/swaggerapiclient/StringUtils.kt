package se.plilja.swaggerapiclient


fun String.capitalizeFirst(): String {
    return this[0].toUpperCase() + this.substring(1)
}

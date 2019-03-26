package com.seanshubin.kotlin.tryme.jvm.datomic

import com.seanshubin.kotlin.tryme.common.compare.NaturalSort
import khttp.get

fun main(args: Array<out String>) {
    val datomicPageText = get("https://my.datomic.com/downloads/free").text
    val pattern = Regex("/downloads/free/(.*)/changes")
    val matchResults = pattern.findAll(datomicPageText)
    val versions = matchResults.map { it.groupValues[1] }
    val comparator = NaturalSort
    val sortedVersions = versions.sortedWith(comparator.reversed())
    val iterator = sortedVersions.iterator()
    val latestVersion = iterator.next()
    val url = "https://my.datomic.com/downloads/free/$latestVersion"
    println("latest version is at $url")
}

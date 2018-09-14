# Kotlin Language Review

## Sean's favorite features
- statically typed
- language very well documented
- great ide support
- common modules (multiplatform projects)
- null safety
- smart casts
- [extension functions](https://kotlinlang.org/docs/reference/extensions.html)
- data classes
- [function literals with receiver](https://kotlinlang.org/docs/reference/type-safe-builders.html)
    - [type safe builders](https://kotlinlang.org/docs/reference/type-safe-builders.html)
- un-opinionated regarding functional and oo 
- coroutines
- takes from java
    - enumerated types
    - uses regular java collections
- takes from scala
    - data classes
    - companion objects
    - primary constructor
- can destructure data classes as if they were a tuple
- operator overloading
    - uses predefined names
    - can't make your own
    - explicit "operator" keyword
- delegation

## What to watch out for
- Poor documentation for experimental features, it took me way too long to figure out how to get a simple "hello world" multiplatform project working
- While scala uses its own immutable collections, kotlin uses immutable views around mutable collections

package io.github.composefluent

/**
 * Marks declarations that are part of an experimental fluent API.
 *
 * Experimental fluent APIs are subject to change or removal in future releases.
 * The design and structure of these APIs are not considered stable, and breaking
 * changes may occur without a major version increment.
 *
 * Using elements marked with this annotation requires explicit opt-in, either by
 * applying the `@OptIn(ExperimentalFluentApi::class)` annotation to the calling site
 * or by enabling the `-opt-in=io.github.composefluent.ExperimentalFluentApi` compiler
 * argument.
 *
 * **Warning:** Use of experimental fluent APIs should be carefully considered, as
 * they might introduce unforeseen compatibility issues or unexpected behavior changes
 * when updating to newer versions of the library.
 */
@RequiresOptIn(message = "This is an experimental fluent API.")
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalFluentApi
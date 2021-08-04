/**
 * indicate all the widget types classified by its function
 */
enum class ActualType{
    /**
     * simple line
     */
    LINE,

    /**
     * button , has float , hover and pressed state
     * (when has pressed state , just like the [CHECKBOX])
     */
    BUTTON,

    /**
     * only editable at eit mode , just for show constant info
     */
    TEXT,

    /**
     * interactive widget , like the search bar
     */
    INPUT,

    /**
     * has full float , hover and pressed state
     */
    CHECKBOX,

    /**
     * working process
     */
    PROCESS,

    /**
     * decoration
     */
    SPLIT,
    DIV,

    /**
     * render world in gui
     */
    THREE_DIM,

    /**
     * unclassified
     */
    MISC,

    /**
     * scroll bar , interactive widgets
     */
    SLIDE,

    /**
     * gauge , show specific percentage info
     */
    METER,

    /**
     * gauge , Specialized for liquid info
     */
    TANK,

    /**
     * gauge , Specialized for energy info
     */
    CELL;


}
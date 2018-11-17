fun main(args: Array<String>) {
    val head = create7Nodes()
    println(head)
    val removeNthFromEnd = Solution().middleNode(head)
    println(removeNthFromEnd)
}

private fun create7Nodes(): ListNode {
    val head = ListNode(1)
    val listNode2 = ListNode(1)
    head.next = listNode2
    val listNode3 = ListNode(3)
    listNode2.next = listNode3
    val listNode4 = ListNode(3)
    listNode3.next = listNode4
    val listNode5 = ListNode(4)
    listNode4.next = listNode5
    val listNode6 = ListNode(4)
    listNode5.next = listNode6
    val listNode7 = ListNode(7)
    listNode6.next = listNode7
    return head
}

data class ListNode(var `val`: Int = 0) {
    var next: ListNode? = null
    override fun toString(): String {
        return "$`val` -> $next"
    }

}

class Solution {

    fun swapPairs(head: ListNode?): ListNode? {
        var iterator = head
        val nextHead: ListNode? = iterator?.next ?: return iterator
        var previousHead: ListNode? = null
        while (iterator?.next != null) {
            iterator?.next?.let {
                iterator?.next = it.next
                it.next = iterator
                previousHead?.next = it
                previousHead = iterator
            }
            iterator = iterator?.next
        }
        return nextHead
    }

    fun reverse(head: ListNode): ListNode? {
        val (_, newHead) = reverse(head, head.next)
        return newHead
    }

    private fun reverse(head: ListNode, next: ListNode?): Pair<ListNode, ListNode> {
        if (next == null) {
            return Pair(head, head)
        }
        val (reversed, masterHead) = reverse(next, next.next)
        reversed.next = head
        head.next = null
        return Pair(head, masterHead)
    }

    fun deleteDuplicates(head: ListNode?): ListNode? {
        val dummyHead = ListNode(Int.MIN_VALUE)
        dummyHead.next = head ?: return null
        var previousHead = dummyHead
        var current: ListNode? = previousHead
        var duplicated = Int.MIN_VALUE
        while (current != null) {
            current.next?.let { next ->
                if (next.`val` == current?.`val` || current?.`val` == duplicated) {
                    duplicated = current!!.`val`
                } else {
                    previousHead.next = current
                    previousHead = current!!
                }
            } ?: run {
                if (current?.`val` != duplicated) {
                    previousHead.next = current
                } else {
                    previousHead.next = null
                }
            }
            current = current.next
        }
        return dummyHead.next
    }

    fun middleNode(head: ListNode?): ListNode? {
        var current = head
        var middle = current
        while (current?.next != null) {
            middle = middle?.next
            current = current.next?.next
        }
        return middle
    }
}
fun main(args: Array<String>) {
    val head = create7Nodes()
    println(head)
    val removeNthFromEnd = Solution().swapPairs(head)
    println(removeNthFromEnd)
}

private fun create7Nodes(): ListNode {
    val head = ListNode(1)
    val listNode2 = ListNode(2)
    head.next = listNode2
    val listNode3 = ListNode(3)
    listNode2.next = listNode3
    val listNode4 = ListNode(4)
    listNode3.next = listNode4
    val listNode5 = ListNode(5)
    listNode4.next = listNode5
    val listNode6 = ListNode(6)
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
}
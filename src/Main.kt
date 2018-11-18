fun main(args: Array<String>) {
    val head = create7Nodes()
    val head1 = create7Nodes()
    println(head)
    val removeNthFromEnd = Solution().mergeKLists(arrayOf(head, head1))
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
//    listNode5.next = listNode6
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

    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        var iterator: ListNode? = head ?: return null
        var subGroupCounter = 1
        while (subGroupCounter < k && iterator?.next != null) {
            subGroupCounter++
            iterator = iterator.next
        }
        return if (subGroupCounter == k) {
            val nextSubGroupHead = iterator?.next
            iterator?.next = null
            val reversed = reverse(head)
            head.next = reverseKGroup(nextSubGroupHead, k)
            reversed
        } else {
            head
        }
    }

    fun rotateRight(head: ListNode?, k: Int): ListNode? {
        val size = size(head)
        val k1 = k % size
        if (k1 == 0 || head?.next == null) {
            return head
        }
        println(size)
        println(k)
        println(k1)
        val (newHead, _, last) = rotateRightRecursively(head, k1)
        last?.next = head
        return newHead
    }

    private fun size(head: ListNode?): Int {
        var current = head
        var count = 0
        while (current != null) {
            count++
            current = current.next
        }
        return count
    }

    private fun rotateRightRecursively(head: ListNode?, k: Int): Triple<ListNode?, Int, ListNode?> {
        if (head?.next?.next == null) {
            val last = head?.next
            head?.next = null
            return Triple(last, k - 1, last)
        }
        val (listNode, newK, last) = rotateRightRecursively(head.next, k)
        if (newK > 0) {
            val next = head.next
            next?.next = listNode
            head.next = null
            return Triple(next, newK - 1, last)
        }
        return Triple(listNode, newK, last)
    }

    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val dummyNode = ListNode(Int.MIN_VALUE)
        var currentNode = dummyNode
        val newList = lists.toMutableList()
        var smallestNode = getSmallestNode(newList)
        while (smallestNode != null) {
            currentNode.next = smallestNode
            currentNode = currentNode.next!!
            smallestNode = getSmallestNode(newList)
        }
        return dummyNode.next
    }

    private fun getSmallestNode(lists: MutableList<ListNode?>): ListNode? {
        var index = -1
        var largestValue = Int.MAX_VALUE
        var listNode: ListNode? = null
        lists.indices.forEach {
            val node = lists[it]
            if (node != null && node.`val` <= largestValue) {
                largestValue = node.`val`
                index = it
            }
        }
        if (index != -1) {
            listNode = lists[index]
            listNode?.next?.let {
                lists[index] = it
            } ?: run {
                lists.removeAt(index)
            }
        }
        listNode?.next = null
        return listNode
    }

    
}
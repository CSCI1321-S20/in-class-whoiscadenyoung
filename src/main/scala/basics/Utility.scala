package basics

object Utility extends App {
    // A is variable type; if you name type param Int, then you can't use the real Int in function; you're shadowing the name
    def findAndRemove[A](lst: List[A])(pred: A => Boolean): (Option[A], List[A]) = {
        def helper(rest: List[A]): (Option[A], List[A]) = rest match {
            case Nil => (None, Nil)
            case h :: t => 
                if (pred(h)) (Some(h), t) else {
                    val (o, l) = helper(t)
                    (o, h :: l)
                }
        }
        helper(lst)
    }
    val nums = List(7, 5, 3, 8, 2, 6)
    println(findAndRemove(nums)(_ % 2 == 0))
}
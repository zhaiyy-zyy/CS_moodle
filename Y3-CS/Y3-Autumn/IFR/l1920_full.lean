/-
  Lecture 19 & 20: 
    1) Permutation
    2) Expression Tree
-/

namespace l1920
set_option pp.structure_projections false
-- use the natural number
open nat
open list

variables {A B C D: Type}

-- definition of less than or equal
def le (m n : ℕ): Prop :=
  ∃ k : ℕ, k + m = n
local notation (name := le) m ≤ n := le m n

-- definition of le on bools
def leb : ℕ → ℕ → bool 
| 0 n := tt
| (succ m) 0 := ff
| (succ m) (succ n) := leb m n

/- definition of if-then-else -/
--一个布尔值，真的返回a，假的返回b
def ite : bool → A → A → A
| tt a b := a
| ff a b := b
local notation (name := ite) 
  if x then a else b := ite x a b

-- definition of insert
--自然数插入列表，n小插入开头，n大插入结尾
def insert : ℕ → list ℕ → list ℕ 
| n [] := [n]   
| n (m :: ns) := 
    if (leb n m) then n :: m :: ns  -- n is added at front if n ≤ m
                 else m :: (insert n ns) -- otherwise, insert n into the tail

-- definition of insertion sort
--sort表示自然数列表，插入n需要保持列表有序
def sort : list ℕ → list ℕ 
| [] := []
| (n :: ns) := insert n (sort ns)


/-
  The above are from last week's lectures
-/

/-
We also need to show the sorted list contains the exactly 
the same elements as in the original list.
This is called Permutation.
--Perm表示是否为排列关系,[2,3,1,4] [1,2,3,4]是排列关系，两个列表元素必须一样，顺序无关紧要
Perm : list A → list A → Prop
Perm [2,3,1,4] [1,2,3,4]
¬ Perm [2,3] [2,1,3]
We need to show the following property
∀ l: list ℕ, Perm l (sort l)
-/

/-
The insert relaiton
-/
inductive Insert : A → list A → list A → Prop 
-- insert at the head using cons
--从头开始加1【3，4】=【1，3，4】
| insert_hd : ∀ a:A,∀ as : list A,Insert a as (a :: as)
-- insert someware else
--从第二个开始1【3，4】=【3，1，4】
| insert_tl : ∀ a b : A, ∀ bs abs : list A, 
      Insert a bs abs 
    → Insert a (b::bs) (b::abs)

/- 
How to define the permutation relationship
-/ 
inductive Perm : list A → list A →  Prop
| perm_nil : Perm [] []      -- empty list is the permutation of the empty list
| perm_cons : ∀ a : A, ∀ as bs bs': list A,
  -- if as is the permutation of bs, then insert an a in an arbitrarily position
  -- is equivalent to add the a using cons 
              Perm as bs → Insert a bs bs' → Perm (a :: as) bs'  

open Insert Perm


-- reflexivity
theorem refl_perm : ∀ as : list A, Perm as as :=
begin
  assume as,
  induction as with a as' ih,
  apply perm_nil,
  apply perm_cons,
  exact ih,
  apply insert_hd,
end 

-- show insert is a special case of Insert
lemma insert_insert : ∀ n : ℕ, ∀ ns: list ℕ, 
      Insert n ns (insert n ns):=
begin
  assume n ns,
  induction ns with n' ns' ih,
  -- Insert n nil (insert n nil)
  -- Insert n nil [n]
  dsimp [insert], -- this is exactly what insert at head does
  apply insert_hd,
  -- Insert n (n' :: ns') (insert n (n' :: ns'))
  -- Insert n (n' :: ns') (if (leb n n') then n::n'::ns'
  --                       else n':: insert n ns')
  dsimp [insert],
  -- case analysis
  cases x: (leb n n'),
  --cases (leb n n'),
  dsimp [ite], -- Insert n (n' :: ns') n':: insert n ns'
  -- this is insert into the tail
  -- Insert n ns' (insert n ns')
  apply insert_tl,
  exact ih,
  -- Insert n (n' :: ns') (n::n'::ns')
  dsimp [ite],
  -- this is insert at head
  apply insert_hd,
end


theorem perm_sort : ∀ l: list ℕ, Perm l (sort l) :=
begin
  assume l,
  induction l with a l' ih,
  dsimp[sort],
  apply perm_nil,
  -- Perm (a :: l') (sort (a :: l'))
  -- Perm (a :: l') (insert a (sort l'))
  dsimp[sort],
  -- Perm as bs → Insert a bs bs' → Perm (a :: as) bs' 
  -- Perm (a :: l') (insert a (sort l'))
  -- Perm l' ?
  apply perm_cons,
  exact ih,
  -- ? = (sort l')
  -- Insert a (sort l') (insert a (sort l'))
  -- for ℕ, Insert relation holds for the function insert
  -- insert generate a special case of Insert 
  apply insert_insert,
end


/-
  Definition of Binary Trees 
  1) leaf
  2) lefttree node righttree
-/

inductive Tree : Type
| leaf : Tree    -- a leaf is a tree
| node : Tree → ℕ → Tree → Tree -- a node contains ltree(左子树), a number and rtree（右子树）

open Tree

/-
  Tree Sort:

  The idea of tree sort is very similar to quicksort.
  We turn a list into a tree such that:
    1) for every node, the numbers in its left subtree is smaller the number
    of this node which is smaller than all the numbers in its right subtree
-/

-- Assuming we have a tree which is already sorted, we need to know what to do
-- if we insert a number into it, and we want to keep it sorted
--将一个整数 n 插入到一棵二叉树中，同时保持树的排序性质。
def ins : ℕ → Tree → Tree
| n leaf := node leaf n leaf
| n (node l m r) :=
          if (leb n m)      -- if n is less than or equal to m
          then node (ins n l) m r -- then insert it into the left subtree
          else node l m (ins n r)

-- Then we turn a list into a sorted tree
--将一个整数列表转化为排序二叉树
--a 为第一个元素，as 为剩下的列表，先递归地将剩余列表 as 转换成一棵二叉树，然后将当前元素 a 插入
def list2tree : list ℕ → Tree
| [] := leaf
| (a::as) := ins a (list2tree as)


-- Finally, we need to covert a tree into a list
--将一棵二叉树 (Tree) 转换为一个有序的整数列表
--输入是一个 Tree，可以是leaf：表示空树；(node l m r)：表示包含左子树 l、当前节点值 m 和右子树 r 的树
def tree2list : Tree → list ℕ 
| leaf := []
| (node l m r) := (tree2list l) ++ (m :: tree2list r)

-- sanity check
#eval tree2list(list2tree [3,2,9,4,1])

def treesort (n : list ℕ ) : list ℕ :=
  tree2list(list2tree n)

#eval treesort [3,2,9,4,1]

/-
  Expression Tree:
    a + 2 * b

        +
      /   \
    a       *
          /   \
        2       b
-/

-- inductive definition of an expression tree for this example
inductive Expr : Type
| const : ℕ → Expr
| var : string → Expr
| plus : Expr → Expr → Expr
| times : Expr → Expr → Expr

open Expr
-- examples: x * (y + 2)
-- how to define it?
def e1 : Expr := times (var "x") (plus (var "y") (const 2))
-- Excercise: try e2, x * y + 2
def e2 : Expr := plus (times (var "x") (var "y")) (const 2)


-- how to evaluate the expression?
-- what we have here is something like the syntax of an expression
-- or the grammar of a language (i.e., what are valid expressions).
-- To understand the expression, we still need to know its semantics
-- Therefore, we need a compiler or an interpreter to run the code

-- Can we define an evaluator which takes an expression and returns a
-- natural number? e.g., f: Expr → ℕ 
-- As we have variables, we need an environment to provide variable assignment

def Env : Type :=
    string → ℕ  -- This is no ideal, as all variables assignment needs to be defined


-- Then we could evalaute an expression based on a given environment
-- This function essentially acts as the semantics of the expression
def eval : Expr → Env → ℕ
| (const n) _ := n
| (var x) env := env x
| (plus e1 e2) env := (eval e1 env) + (eval e2 env)
| (times e1 e2) env := (eval e1 env) * (eval e2 env)

-- We assume undefined variables all equal to 0
def env0: Env
| "x" := 3
| "y" := 5
|  _  := 0


end l1920
/-
  Lecture 19 & 20: 
    1) Compiler
    2) Tree Sort
-/

namespace l2122
set_option pp.structure_projections false
-- use the natural number
open nat
open list

variable {A : Type}



-- inductive definition of an expression tree
inductive Expr : Type
| const : ℕ → Expr
| var : string → Expr
| plus : Expr → Expr → Expr
| times : Expr → Expr → Expr

open Expr

def Env : Type :=
    string → ℕ 

-- This function essentially acts as the semantics of the expression
def eval : Expr → Env → ℕ
| (const n) _ := n
| (var x) env := env x
| (plus e1 e2) env := (eval e1 env) + (eval e2 env)
| (times e1 e2) env := (eval e1 env) * (eval e2 env)

/- The above are from the last lecture -/


-- Stack Machine
-- A list of instructions, this is something like the syntax 
inductive Instr : Type
| pushC : ℕ → Instr -- push a natural number on to the stack
| pushV : string → Instr -- push a variable on to the stack
| add : Instr -- add two natural numbers
| mult : Instr -- multiplication

open Instr

-- Then we could define code as a list of instructions
@[reducible] -- it means we could do pattern matching on Code
def Code : Type :=
  list Instr

-- in order to interpret the code, we also need a stack of natural number
def Stack : Type :=
  list ℕ 

-- the function to run the code, (semantics)
def run : Code → Stack → Env → ℕ -- we need an environment, since we have variables
-- if the given code is empty (all codes have been executed)
| [] [n] _ := n          -- we pick the only number in the stack
-- if the next instruction is pushC
| (pushC n ::l) s env := 
-- we add the given natural number to the stack
          run l (n::s) env 
-- if the next instruction is pushV
| (pushV x ::l) s env :=  
-- the variable needs to be evaluated and then added to the stack
          run l (env x :: s) env
-- if the next instruction is add
| (add :: l) (m :: n :: s) env :=
-- we take the first two numbers from the stack, add them together
-- then the results is added to the stack
          run l ((n+m) :: s) env -- question: does order matters? m+n or n+m
-- if the next instruction is mult
| (mult :: l) (m :: n :: s) env :=
-- we take the first two numbers from the stack, do multiplication
-- then the results is added to the stack
          run l ((n*m) :: s) env
| _ _ _ := 0 -- otherwise, we have a problem and for now we return 0

-- examples: x * (y + 2)
def e1 : Expr :=
  times (var "x") (plus (var "y") (const 2))

-- We assume undefined variables all equal to 0
def env0: Env
| "x" := 3
| "y" := 5
|  _  := 0

-- x * (y + 2) is equivalent to:
-- (pushV x) :: (pushV y) :: (pushC 2) :: add :: mult :: []
def c1 : Code :=
  [(pushV "x"), (pushV "y"), (pushC 2), add, mult]

#eval (run c1 [] env0)
#eval eval e1 env0


-- we don't want to do the translation manually, thus we need a compiler
-- a good compiler should be both efficient,
-- and easy to verify
/-
def compile : Expr → Code
| (const n)  := [pushC n]
| (var x)    := [pushV x]
| (plus l r) := (compile l) ++ (compile r) ++ [add]
| (times l r) := (compile l) ++ (compile r) ++ [mult]

#eval (run (compile e1) [] env0)
-/

-- is this a good compiler? why?
-- think about append, reverse and fast-reverse

-- continuation-based compiler
-- given the expression, and the code comes after, we return the full code
def compile_aux : Expr → Code → Code 
| (const n) c  := pushC n :: c
| (var x) c    := pushV x :: c
-- (compile l) ++ (compile r) ++ [add] ++ c
-- (compile l) ++ (compile r) ++ (add :: c)
-- (compile l) ++ (compile_aux r (add ::c))
-- compile_aux l (compile_aux r (add ::c))
| (plus l r) c := compile_aux l (compile_aux r (add ::c))
| (times l r) c := compile_aux l (compile_aux r (mult ::c))

def compile : Expr → Code
| e := compile_aux e []


theorem compile_aux_ok : ∀ e : Expr, ∀ c : Code, ∀ s : Stack, ∀ env : Env,
  --- eval e env = the sequence of the code at the beginning
      run (compile_aux e c) s env = run c ((eval e env) :: s) env :=
begin
  assume e,
  induction e,
  case const : n {
    assume c s env,
    --dsimp [compile_aux, eval, run],
    refl,
  },
  case var : x {
    assume c s env,
    --dsimp [compile_aux, eval, run],
    refl,
  },
  case plus : m n ih1 ih2{
    assume c s env,
    dsimp [compile_aux],
    -- run (compile_aux m (compile_aux n (add :: c))) s env = 
    -- run c (eval (plus m n) env :: s) env
    rewrite ih1,
    -- run (compile_aux n (add :: c)) (eval m env :: s) env = 
    -- run c (eval (plus m n) env :: s) env
    rewrite ih2,
    -- run (compile_aux (add :: c)) (eval n env :: eval m env :: s) env = 
    -- run c (eval (plus m n) env :: s) env
    dsimp[eval],
    dsimp[run],
    refl,
  },
  case times : m n ih1 ih2{
    assume c s env,
    dsimp [compile_aux],
    rewrite ih1,
    rewrite ih2,
    dsimp [eval, run],
    refl,
  },
end

-- we cannot prove this directly as it is defined based on compile_aux with 
-- one of the input to be null, i.e., this is a special case
theorem compiler_ok : ∀ e : Expr, ∀ env : Env, 
    eval e env = run (compile e) [] env :=
begin
  assume e env,
  dsimp [compile],
  rewrite compile_aux_ok,
  dsimp [run],
  refl,
end


end l2122
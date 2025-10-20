/-
COMP2068 IFR 
Exercise 03

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/



namespace proofs


def bnot : bool → bool 
| tt := ff
| ff := tt 

def band : bool → bool → bool 
| tt b := b
| ff b := ff

def bor : bool → bool → bool 
| tt b := tt
| ff b := b

def is_tt : bool → Prop 
| tt := true
| ff := false


local notation (name := band) x && y := band x y 
local notation (name := bor) x || y := bor x y


prefix `!`:90 := bnot

/- --- Do not add/change anything above this line --- -/


theorem q01: ¬ (∀ x : bool, ! x = x) :=
begin
    assume h,
    have hfalse : !false = false,
    apply h,
    contradiction,
end

theorem q02: ∀ x:bool,∃ y:bool, x = y :=
begin 
    assume x,
    existsi x,
    refl,
end


theorem q03: ¬ (∃ x:bool,∀ y:bool, x = y) :=
begin
    assume h,
    cases h with b nb,
    cases b,
    have h1 : ff=tt,
    apply nb,
    cases h1,
    have h2 : tt=ff,
    apply nb,
    cases h2,
end

theorem q04: ∀ x y : bool, x = y → ! x = ! y :=
begin
    assume x,
    assume y,
    assume h,
    rewrite h,
end

theorem q05: ∀ x y : bool, !x = !y → x = y :=
begin
    assume a b,
    assume nab,
    cases a,
    cases b,
    refl,
    contradiction,
    cases b,
    contradiction,
    refl,
end


theorem q06: ¬ (∀ x y z : bool, x=y ∨ y=z)  :=
begin
    assume h,
    have h1:tt = ff ∨ ff = tt,
    apply h,
    cases h1,
    cases h1,
    contradiction,
end 

theorem q07: ∃ b : bool, ∀ y:bool, b || y = y :=
begin
    --使用existsi ff指定b=ff，满足存在性条件
    existsi ff,
    --引入y
    assume y,
    --分类讨论
    cases y,
    --当y=ff，b||y=ff∨ff=ff,满足refl
    refl,
    --当y=tt，b||ff∨tt=tt,满足refl
    refl,
end

theorem q08: ∃ b : bool, ∀ y:bool, b || y = b :=
begin
    --使用existsi tt指定b=tt，满足存在性条件
    existsi tt,
    --引入y
    assume y,
    --分类讨论
    cases y,
    --当y=tt，b∨y=tt∨ff=tt,满足refl
    refl,
    --当y=tt，b∨y=tt∨tt=tt,满足refl
    refl,
end

theorem q09: ∀ x : bool, (∀ y : bool, x && y = y) ↔ x = tt :=
begin
    assume x,
    constructor,
    assume h,
    cases x,
    apply h,
    refl,
    assume h,
    assume y,
    cases y,
    rw h,
    refl,
    rw h,
    refl,
end

theorem q10: ¬ (∀ x y : bool, x && y = y ↔ x = ff) :=
begin
    assume h,
    have h1:tt && tt = tt ↔ tt = ff,
    apply h,
    cases h1 with h2 h3,
    have h4:tt=ff,
    apply h2,
    refl,
    cases h4,
end

variables (A : Type) (PP : A → Prop) (Q : Prop)

-- Attempting to prove equivalence
example : (∀ x : A, PP x ∧ Q) ↔ ((∀ x : A, PP x) ∧ Q) :=
begin
  split,
  { -- Forward direction: (∀ x : A, PP x ∧ Q) → ((∀ x : A, PP x) ∧ Q)
    assume h,
    split,
    { -- Prove ∀ x : A, PP x
      assume x,
      exact (h x).left, -- Extract PP x from h
    },
    { -- Prove Q
      exact (h (arbitrary A)).right, -- Extract Q (must hold for any x)
    }
  },
  { -- Reverse direction: ((∀ x : A, PP x) ∧ Q) → (∀ x : A, PP x ∧ Q)
    assume h,
    assume x,
    split,
    { -- Prove PP x
      exact h.left x, -- Extract PP x from h.left
    },
    { -- Prove Q
      exact h.right, -- Extract Q from h.right
    }
  }
end

variables (A : Type) (PP : A → Prop) (Q : Prop)

-- Attempting to prove equivalence
example : (∀ x : A, PP x ∧ Q) ↔ ((∀ x : A, PP x) ∧ Q) :=
begin
  constructor,
  assume h,
  
end

/- ---Do not add/change anything below this line --- -/
end proofs

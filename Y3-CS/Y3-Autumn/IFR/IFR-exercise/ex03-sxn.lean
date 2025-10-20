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

/-If the above two lines report errors, then use 
local notation  x && y := band x y 
local notation  x || y := bor x y
-/


prefix `!`:90 := bnot

/- --- Do not add/change anything above this line --- -/



theorem q01: ¬ (∀ x : bool, ! x = x) :=
begin
    assume h,
    have h1: !ff=ff,
    apply h,
    change is_tt ff,
    rewrite <- h1,
    dsimp [bnot],
    constructor,
end

theorem q02: ∀ x:bool,∃ y:bool, x = y :=
begin 
    assume x,
    cases x,
    existsi ff,
    refl,
    existsi tt,
    refl,
end


theorem q03: ¬ (∃ x:bool,∀ y:bool, x = y) :=
begin
    assume h,
    cases h with x h1,
    cases x,
    have f: ff =tt,
    apply h1,
    contradiction,
    have f: tt=ff,
    apply h1,
    contradiction,
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
    assume x y,
    cases x,
    cases y,
    assume h,
    refl,
    dsimp [bnot],
    assume h,
    rewrite h,
    cases y,
    dsimp [bnot],
    assume h,
    rewrite h,
    dsimp [bnot],
    assume h,
    refl,
end


theorem q06: ¬ (∀ x y z : bool, x=y ∨ y=z)  :=
begin
    assume h,
    have h1 : tt=ff ∨ ff=tt,
    apply h,
    --???
    cases h1 with xy yz,
    contradiction,
    contradiction,
end 

theorem q07: ∃ b : bool, ∀ y:bool, b || y = y :=
begin
    existsi ff,
    dsimp [bor],
    assume tt,
    refl,
end

theorem q08: ∃ b : bool, ∀ y:bool, b || y = b :=
begin
    existsi tt,
    dsimp [bor],
    assume y,
    refl,
end

theorem q09: ∀ x : bool, (∀ y : bool, x && y = y) ↔ x = tt :=
begin
    assume x,
    constructor,
    assume h,
    cases x,
    dsimp [band] at h,
    apply h,
    --??
    refl,
    assume h,
    assume y,
    rewrite h,
    dsimp [band],
    refl,
end

theorem q10: ¬ (∀ x y : bool, x && y = y ↔ x = ff) :=
begin
    assume h,
    have h1 : tt && tt = tt ↔ tt = ff,
    apply h,
    cases h1 with a b,
    have h2 : tt = ff,
    apply a,
    dsimp [band],
    --refl,
    constructor,
    contradiction,
end

/- ---Do not add/change anything below this line --- -/
end proofs

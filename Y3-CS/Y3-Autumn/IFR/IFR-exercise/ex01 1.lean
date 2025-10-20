/-
COMP2068 IFR 
Exercise 01

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture
(i.e., assume, exact, apply)

-/

namespace proofs

variables P Q R : Prop

/- ---Do not add/change anything above this line --- -/

theorem q01: Q → Q :=
begin
  assume Q,
  exact Q,
end

theorem q02: P → Q → P :=
begin 
  assume p,
  assume p2q,
  exact p,
end


theorem q03: P → Q → Q :=
begin
  assume p,
  assume q,
  exact q,
end

theorem q04: (P → Q → R) → (P → Q) → P → R :=
begin
  assume p2q2r,
  assume p2q,
  assume p,
  apply p2q2r,
  apply p,
  apply p2q,
  exact p,
end

theorem q05: P → ¬ ¬ P :=
-- P -> ((P -> f) -> f)
begin
  assume P,
  assume notp,
  -- P -> Q
  -- P
  apply notp,
  exact P,
end


theorem q06: (¬ ¬ ¬ P) → (¬ P) :=

begin
  assume notnotnotp,
  assume P,
  apply notnotnotp,
  assume notp,
  apply notp,
  exact P,
end 

/- ---Do not add/change anything below this line --- -/
end proofs

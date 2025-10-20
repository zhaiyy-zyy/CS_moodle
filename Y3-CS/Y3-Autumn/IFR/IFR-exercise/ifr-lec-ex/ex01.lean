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
  assume q,
  exact q,
end

theorem q02: P → Q → P :=
begin 
  assume p q,
  exact p,
end


theorem q03: P → Q → Q :=
begin
  assume p q,
  exact q,
end

theorem q04: (P → Q → R) → (P → Q) → P → R :=
begin
  assume pqr pq p,
  apply pqr,
  exact p,
  apply pq,
  exact p,
end

theorem q05: P → ¬ ¬ P :=
begin
  assume p,
  assume notp,
  apply notp,
  exact p,
end


theorem q06: ¬ ¬ ¬ P → ¬ P :=
begin
  assume notnotnotp,
  assume p,
  apply notnotnotp,
  assume notp,
  apply notp,
  exact p,
end 

/- ---Do not add/change anything below this line --- -/
end proofs

variables P Q R : Prop

#check P → Q

-- Implication \-> 

#check P ∧ Q

-- Conjunction \and 

#check P ∨ Q

-- Disjunction \or

#check ¬ P 

-- Negation \neg
-- ¬ P = (P → false)

#check P ↔ Q

-- Equivalence \iff
-- P ↔ Q = (P → Q) ∧ (Q → P)

theorem I: P → P :=
begin
  assume h,
  exact h,
end

theorem C : (P → Q) → (Q → R) → P → R :=
begin
  assume p2q,
  assume q2r,
  assume p,
  apply q2r,
  apply p2q,
  exact p,
end


variables P Q R : Prop

open classical


theorem dm1: ¬ (P∨ Q)↔ ¬ P∧ ¬ Q:=
begin
constructor,
assume h1,
constructor,
assume p,
apply h1,
left,
exact p,
assume q,
apply h1,
right,
exact q,
assume h2,
cases h2 with np nq,
assume h3,
cases h3 with p q,
apply np,
exact p,
apply nq,
exact q,
end
theorem dm2: ¬ (P∧ Q)↔ ¬ P∨ ¬ Q:=
begin 
constructor,
assume h1,
right,
assume q,
apply h1,
constructor,
sorry,
exact q,
assume h2,
assume pq,
cases pq with p q,
cases h2 with np nq,
apply np,
exact p,
apply nq,
exact q,
end
theorem dm2_em: ¬ (P ∧ Q)→ ¬ P ∨ ¬ Q:=
begin
assume h1,
cases em P with p np,
right,
assume q,
apply h1,
constructor,
exact p,
exact q,
left,
exact np,
end
theorem raa : ¬ ¬ P→ P:=
begin
assume h1,
cases em P with p np,
exact p,
have f:false,
apply h1,
exact np,
cases f,
end
theorem nn_em:¬ ¬ (P∨ ¬ P):=
begin 
assume h1,
apply h1,
right,
assume p,
apply h1,
left,
exact p,
end
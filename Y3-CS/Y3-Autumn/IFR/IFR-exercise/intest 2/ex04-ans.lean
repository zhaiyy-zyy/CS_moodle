/-
COMP2068 IFR 
Exercise 04

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/



namespace ifr_ex04
set_option pp.structure_projections false
open nat

/-
inductive nat : Type
| zero : nat
| succ : nat → nat
-/

def is_zero: ℕ → Prop
| zero := true
| (succ n) := false

example : ∀ n : ℕ, 0 ≠ succ n :=
begin
    assume n,
    assume h,
    --change is_zero (succ n),
    --rewrite <- h,
    --constructor,
    contradiction,
end 


def pred: ℕ → ℕ 
| zero := zero
| (succ n) := n 



theorem inj_succ : ∀ m n : nat, succ m = succ n → m = n :=
begin
    assume m n,
    assume h,
    --change pred(succ m) = pred(succ n),
    --rewrite h,
    injection h,
end


def double : ℕ → ℕ
|  zero := zero 
|  (succ n) := succ(succ (double n))

#reduce (double 4)
  
def half : ℕ → ℕ 
| zero := zero 
| (succ zero) := zero
| (succ (succ n)) := succ (half n)  


#reduce (half 6)

#check congr_arg succ 

theorem half_double : ∀ n : ℕ, half (double n) = n := 
begin
    assume n,
    induction n with n' ih,
    dsimp [half, double],
    refl,
    dsimp [half, double],
    apply congr_arg succ,
    exact ih,
end



def add : ℕ → ℕ → ℕ  
| m zero := m
| m (succ n) := succ (add m n)


local notation m + n := add m n 



theorem add_rneutr : ∀ n : ℕ, n + 0 = n :=
begin
    assume n,
    refl,
end 

theorem add_lneutr : ∀ n : ℕ, 0 + n = n :=
begin
    assume n,
    induction n with n' ih,
    refl,
    apply congr_arg succ,
    exact ih,
end 

theorem add_assoc : ∀ l m n : ℕ, (l + m) + n = l + (m + n) :=
begin
    assume l m n,
    induction n with n' ih,
    refl,
    apply congr_arg succ,
    exact ih,
end


lemma lem :  ∀ m n : ℕ, n + succ m = succ n + m :=
begin
    assume m n,
    induction m with m' ih,
    refl,
    apply congr_arg succ,
    exact ih,  
end

theorem add_comm : ∀ m n : ℕ, m + n = n + m :=
begin
    assume m n, 
    induction n with n' ih,
    symmetry,
    apply add_lneutr,
    have h: m + succ n' = succ (m + n'),
    refl,
    rewrite h,
    rewrite ih,
    have h2: succ (n'+m) = n' + succ m,
    refl,
    rewrite h2,
    apply lem,
end


def mul : ℕ → ℕ → ℕ
| m zero := zero
| m (succ n) := add (mul m n) m


local notation m * n := mul m n 
 


theorem mult_rneutr : ∀ n : ℕ, n * 1 = n :=
begin
  assume n,
  induction n with n' ih,
  refl,
  apply congr_arg succ,
  dsimp [(*), (+)],
  apply add_lneutr,
end



theorem mult_lneutr : ∀ n : ℕ, 1 * n  = n :=
begin
  assume n,
  induction n with n' ih,
  refl,
  apply congr_arg succ,
  dsimp [(*), (+)],
  exact ih,
end






theorem mult_zero_l : ∀ n : ℕ , 0 * n = 0 :=
begin
  assume n,
  induction n with n' ih,
  refl,
  dsimp [(*)],
  rewrite ih,
  refl,
end 



theorem mult_zero_r : ∀ n : ℕ , n * 0 = 0 :=
begin
  assume n,
  refl,
end



lemma lem1 : ∀ n m : ℕ, (n + 1) * m = n * m + m :=
begin
    assume n m,
    induction m with m' ih,
    refl,
    dsimp [(*)],
    rewrite ih,
    have h1: n * m' + n + succ m' = n * m' + n + (m'+1),
    refl,
    rewrite h1,
    have h2: n * m' + n + (m' + 1) = n * m' + (n + (m' + 1)),
    apply add_assoc,
    rewrite h2,
    have h3: n + (m' +1) = (n + m') + 1, 
    symmetry,
    apply add_assoc,
    rewrite h3,
    have h4: n + m' = m' + n,
    apply add_comm,
    rewrite h4,
    have h5: n * m' + m' + (n+1) = n * m' + (m' + (n+1)),
    apply add_assoc,
    rewrite h5,
    have h6: m' + n + 1 = m' + (n+1),
    apply add_assoc,
    rewrite h6,
end



theorem mult_comm :  ∀ m n : ℕ , m * n = n * m :=
begin
  assume m n,
  induction n with n' ih,
  symmetry,
  apply mult_zero_l,
  dsimp [(*)],
  rewrite ih,
  symmetry,
  apply lem1,
end




theorem mult_distr_l :  ∀ l m n : ℕ , (m + n) * l = m * l + n * l :=
begin
  assume l m n,
  induction l with l' ih,
  refl,
  dsimp [(*)],
  rewrite ih,
  have h1: m*l' + m + (n*l'+n) = m*l' +(m +(n*l' +n)),
  apply add_assoc,
  rewrite h1,
  have h2: m + (n* l' +n) = (m + n*l') + n,
  symmetry,
  apply add_assoc,
  rewrite h2,
  have h3: m + n*l' = n* l'+m,
  apply add_comm,
  rewrite h3,
  have h4: n*l'+m + n = n *l' +(m+n),
  apply add_assoc, 
  rewrite h4,
  apply add_assoc,
end



theorem mult_distr_r :  ∀ l m n : ℕ , l * (m + n) = l * m + l * n :=
begin
  assume l m n,
  have h1: l * (m + n) = (m + n) * l,
  apply mult_comm,
  have h2: l * m = m * l,
  apply mult_comm,
  have h3: l * n = n * l,
  apply mult_comm,
  rewrite h1,
  rewrite h2,
  rewrite h3,
  apply mult_distr_l,
end



theorem mult_assoc : ∀ l m n : ℕ , (l * m) * n = l * (m * n) :=
begin
  assume l m n,
  induction n with n' ih,
  refl,
  dsimp [(*)],
  rewrite ih,
  symmetry,
  apply mult_distr_r,
end




end ifr_ex04

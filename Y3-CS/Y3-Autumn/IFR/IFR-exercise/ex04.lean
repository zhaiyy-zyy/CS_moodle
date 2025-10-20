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


local notation (name := add) m + n := add m n 



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

lemma add_succ_l : ∀ m n :  ℕ,succ m +n = succ (m + n) :=
begin
  assume m n,
  induction n with n' ih,
  dsimp[add],
  refl,
  --
  dsimp[add],
  rw ih,
end 

theorem add_comm : ∀ m n : ℕ, m + n = n + m :=
begin
    assume m n,
    induction n with n' ih,
    dsimp[add],
    rw add_lneutr,
    dsimp[add],
    rw add_succ_l,
    rw ih,
end


def mul : ℕ → ℕ → ℕ
| m zero := zero
| m (succ n) := add (mul m n) m


local notation (name := mult) m * n := mul m n 
 

theorem mult_rneutr : ∀ n : ℕ, n * 1 = n :=
begin
  assume n,
  dsimp[mul],
  rw add_lneutr,
end
/-
theorem mult_rneutr : ∀ n : ℕ, n * 1 = n :=
begin
  assume n,
  induction n with n' ih,
  refl,
  apply congr_arg succ,
  exact ih,
end
-/
theorem mult_lneutr : ∀ n : ℕ, 1 * n  = n :=
begin
  assume n,
  induction n with n' ih,
  dsimp[mul],
  refl,
  --
  dsimp[mul],
  rw ih,
  refl,
end
/-
theorem mult_lneutr : ∀ n : ℕ, 1 * n  = n :=
begin
  assume n,
  induction n with n' ih,
  refl,
  apply congr_arg succ,
  exact ih,
end
-/

theorem mult_assoc : ∀ l m n : ℕ , (l * m) * n = l * (m * n) :=
begin
  assume l m n,
  induction n with n' ih,
  dsimp[mul],
  refl,
  --
  dsimp[mul],
  rw ih,
  rw mult_distr_r,
end


theorem mult_comm :  ∀ m n : ℕ , m * n = n * m :=
begin
  assume m n,
  induction n with n' ih,
  dsimp[mul],
  dsimp[add],
  refl,
  dsimp[mul],
  rw ih,
  dsimp[add],
  rw add_assoc (m*n) n' m,
  rw add_comm n' m,
  rw <- add_assoc (m*n) n' m,
end

theorem mult_zero_l : ∀ n : ℕ , 0 * n = 0 :=
begin
  assume n,
  induction n with n' ih,
  refl,
  exact ih,
end 

theorem mult_zero_r : ∀ n : ℕ , n * 0 = 0 :=
begin
  assume n,
  refl,
end

theorem mult_distr_r :  ∀ l m n : ℕ , l * (m + n) = l * m + l * n :=
begin
  assume l m n,
  induction n with n' ih,
  --dsimp[add],
  --dsimp[mul],
  --dsimp[add],
  refl,
  --
  dsimp[add],
  dsimp[mul],
  rw ih,
  rw add_assoc,
end

theorem mult_distr_l :  ∀ l m n : ℕ , (m + n) * l = m * l + n * l :=
begin
  assume l m n,
  induction l with l' ih,
  dsimp[mul],
  dsimp[add],
  refl,
  --
  dsimp[mul],
  rw ih,
  rw add_comm,
  calc
   m*l'+n*l'+(m+n) = m*l'+(n*l'+(m+n)) : by rw add_assoc
   ... = m*l'+(n*l'+m+n) : by rw <- add_assoc ()
   ... = m * l' + (m+n*l'+n) : by rw add_comm
   ... = m * l' + (m+(n*l'+n))
end

end ifr_ex04

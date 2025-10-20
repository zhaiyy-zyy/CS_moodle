
namespace ifr
set_option pp.structure_projections false
open nat

/-
inductive nat : Type
| zero : nat
| succ : nat → nat
-/

/-#reduce succ (succ (succ zero))

def is_succ: ℕ → Prop
  zero := false,
  (succ n) := true,
-/

--succ 0 =1, succ 1 =2 and so on
--N=nat(自然数)
example : ∀ n : ℕ, 0 ≠ succ n :=
begin
    assume n,
    assume h,
    contradiction,
    --change is_succ zero,
    --rewrite h,
end 

/-def pred : ℕ → ℕ-/

--refl 表示相等
theorem inj_succ : ∀ m n : nat, succ m = succ n → m = n :=
begin
    assume m n,
    assume h, 
    injection h,
    --cases h,
    --refl, 
end




--对自然数乘以2
def double : ℕ → ℕ
|  zero := zero
|  (succ n) := succ (succ (double n))


--对自然数进行除以2
def half : ℕ → ℕ 
|  zero := zero
|  (succ zero) := zero
|  (succ (succ n)) := succ (half n)
    

theorem half_double : ∀ n : ℕ, half (double n) = n := 
begin
    assume n,
    --自然数用induction来分类递归，而不是cases
    --induction n with n ih 会根据n的值对问题进行分解，
    --首先处理基础情况n=0,
    --然后处理归纳步骤n=succ n,并假设ih是归纳假设
    induction n with n' ih,
    refl,
    --通过congr_arg可证明f(a)=f(b)
    apply congr_arg succ,--如果你知道a=b,那么你可以得出succ(a)=succ(b)
    exact ih, 
end



--succ a + b = succ (a + b)
def add : ℕ → ℕ → ℕ  
| m zero := m  -- 0 + m = m
| m (succ n) := succ (add m n) -- m + succ(n) = succ (m + n)



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


theorem add_comm : ∀ m n : ℕ, m + n = n + m :=
begin
    assume m n,
    induction n with n' ihn,
    rw add_lneutr,
    refl,
    rw add_succ,
    rw ihn,
    rw succ_add,
end


--def mul : ℕ → ℕ → ℕ
--| m zero := 0 --0*m = 0
--| m (succ n) := add (mul m n) m --(succ n)*m = n * m + m
 


end ifr

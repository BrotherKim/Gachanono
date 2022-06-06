# 1. 복원추출

# 특징: 아이템을 반복적으로 무한히 뽑을 수 있음

# input: 
# - 확률
# - 시도 횟수

# ex)    
# 확률 -> 10%     
# 시도 횟수 -> 2

# output:
# - 시도 횟수별 뽑기 확률
# - 시행횟수 별 기대확률

from math import factorial

def bin_dist(n, k, p):
    # 확률이 p일 때, n회 시행에서 k번 일어날 확률
    nCk = factorial(n) / (factorial(k) * factorial(n - k))
    pd = nCk * (p**k) * ((1-p)**(n-k))
    return pd

def get_prob_try(p):
    # 시도 횟수별 1번 이상 뽑힐 확률
    prob_dic ={}
    count = 0
    while True:
        prob = 1 - (1 - p)**count # 1 - 한 번도 안 뽑힐 확률 = 한 번 이상 뽑힐 확률
        prob_dic[count]= prob
        count += 1
        if prob >= 0.99:
            break
    return prob_dic

def sampling_with_replacement(prob, try_count):
    prob_of_picked ={} # 첫 번째표, 0부터 try count 까지의 확률
    min_required_trial_prob ={} # 두 번째표

    for i in range(try_count+1):
        prob_of_picked[i] = bin_dist(try_count, i, prob)


    min_required_trial_prob = get_prob_try(prob)
    return prob_of_picked, min_required_trial_prob

def FUNC_5(prob, try_count):
    print(sampling_with_replacement(prob, try_count))
    
table1, table2 = sampling_with_replacement(0.1,2)

# 2. 복원 추출 천장시스템(이전에 작성한 이름은 비복원 추출(잘못된 네이밍인듯함))
# 특징: 
# - 뽑을 수 있는 최대 아이템의 개수를 1개로 한정함(한번도 안뽑히거나, 뽑히거나)
# - 천장 시스템을 고려한다


# 복원추출과 차이점:
# - 최대 추출 횟수에 도달하면 무조건 뽑힌다.

# input: 
# - 확률
# - 시도 횟수(최대 시도 횟수보다 클 수 없다. input 받을 때 확인 필요)
# - 최대 시도 횟수

# ex)    
# 확률 -> 10%     
# 시도 횟수 -> 2    
# 최대 시도 횟수 -> 10(10회에는 무조건 뽑힘)



# output:
# - 시도 횟수별 뽑기 확률
# - 시행횟수 별 기대확률

def bin_dist(n, k, p):
    # 확률이 p일 때, n회 시행에서 k번 일어날 확률
    nCk = factorial(n) / (factorial(k) * factorial(n - k))
    pd = nCk * (p**k) * ((1-p)**(n-k))
    return pd
def get_prob_try_ceiling(p, limit):
    # 시도 횟수별 1번 이상 뽑힐 확률
    prob_dic ={}
    count = 0
    while True:
        prob = 1 - (1 - p)**count # 1 - 한 번도 안 뽑힐 확률 = 한 번 이상 뽑힐 확률
        prob_dic[count]= prob
        count += 1
        if count == limit:
            prob_dic[limit] = 1
            break
    return prob_dic
def sampling_with_replacement_ceiling(prob, try_count, limit):
    prob_of_picked ={} # 첫 번째표, 0부터 try count 까지의 확률
    min_required_trial_prob ={} # 두 번째표
    
    none_prob = bin_dist(try_count, 0, prob)
    prob_of_picked[0] = none_prob
    prob_of_picked[1] = 1 - none_prob

    min_required_trial_prob = get_prob_try_ceiling(prob, limit)
    return prob_of_picked, min_required_trial_prob

def FUNC_4(prob, try_count, limit):
    print(sampling_with_replacement_ceiling(prob, try_count, limit))

table1, table2 = sampling_with_replacement_ceiling(0.1,2,20)

# 3. 구간별 확률(동일 아이템, 이전에 조건부 확률로 이름 붙였었음)

# 특징:
# - 뽑는 횟수에 따라 확률 변동이 있다.(뽑을 수 있는 아이템이 같다.)
# - 시도 횟수별 뽑기 확률, 적당한 이산확률 분포식을 계산하지 못함
# - 구간에 시도횟수가 충분하지 않을 경우 99%와 같이 높은 확률의 시도 횟수가 그래프 입력으로 주어지지 않을 수도 있음


# 입력
# - 구간 리스트 값
# [[1,10],[11,20],[21,30]]
# - 구간별 확률 리스트 값
# [5, 10, 20] <- 퍼센트 값
# - 시도 횟수

# output:
# - 시도 횟수별 뽑기 확률(한 번도 안뽑힐 확률, 1번 뽑힐 확률)
# - 시행횟수 별 기대확률

def never_picked_same(range_list, prob_list, try_pick):
    p = 1
    for range_t, prob in zip(range_list, prob_list):
        if try_pick > range_t[1]:
            p = p*(1-prob)**(range_t[1] - range_t[0] + 1)
        else:
            p = p *(1-prob)**(try_pick - range_t[0] + 1)
            break
    return p
def get_prob_try_interval_same(range_list, prob_list):
    # 시도 횟수별 1번 이상 뽑힐 확률
    prob_dic ={}
    count = 1
    p_no = 1
    for range_s , prob in zip(range_list, prob_list):
        for _ in range(range_s[0], range_s[1]+1):
            p_no = p_no * (1 - prob)
            prob_dic[count]= 1 - p_no
            count += 1
            if 1 - p_no >= 0.99:
                break
    return prob_dic
def prob_by_interval_same_item(range_list, prob_list, try_pick):
    prob_of_picked ={} # 한 번도 안 뽑힐 확률
    min_required_trial_prob ={} # 두 번째표
    never_pick_p = never_picked_same(range_list, prob_list, try_pick)
    prob_of_picked[0] = never_pick_p
    prob_of_picked[1] = 1 - never_pick_p
    min_required_trial_prob = get_prob_try_interval_same(range_list, prob_list)
    return prob_of_picked, min_required_trial_prob
# range_list =[[1,10],[11,20],[21,30]]
# prob_list =[0.05, 0.1, 0.2]

def FUNC_3(range_list, prob_list, try_pick):
    print(sampling_with_replacement(range_list, prob_list, try_pick))

# table1, table2 = prob_by_interval_same_item(range_list,prob_list, 13)

# 4. 구간별 확률

# 특징:
# - 뽑는 횟수 별로 확률 값이 다름(뽑을 수 있는 아이템이 다름)
# - 일반적으로 아래쪽 확률에서는 아이템이 안뽑힘(처음에는 잡템만 뽑힘, 나중에만 좋은 아이템을 뽑을 수 있음)
# - 이 경우에도 target아이템 하나만 뽑으면 된다. 따라서 뽑힌 횟수별 확률그래프 불필요



# 입력
# - 아이템을 확득이 가능한 시작 횟수(ex, 200회 뽑기이후 부터 획득 가능)
# - 해당 구간에서 뽑히는 확률    
# - 시도 횟수(아이템 획득 가능한 횟수보다 더 큰 수가 입력으로 들어와야 한다.)

# output:
# - 시도 횟수별 뽑기 확률(한 번도 안뽑힐 확률, 1번 뽑힐 확률)(table1)
# - 시행횟수 별 기대확률

def never_picked_diff(start_count, prob, try_pick):
    p = 1
    if start_count > try_pick:
        return p
    else:
        p = p * (1-prob)**(try_pick - start_count + 1)
        return p
def get_prob_try_interval_diff(start_count, prob):
    # 시도 횟수별 1번 이상 뽑힐 확률
    prob_dic ={}
    count = start_count
    p_no = 1

    for i in range(start_count):
        prob_dic[i] = 0
    
    while True:
        p_no = p_no * (1 - prob)
        prob_dic[count]= 1 - p_no
        count += 1

        if 1 - p_no >= 0.99:
            break

    return prob_dic
def prob_by_interval_diff_item(start_count, prob, try_pick):
    prob_of_picked ={} # 한 번도 안 뽑힐 확률
    min_required_trial_prob ={} # 두 번째표
    never_picked_value = never_picked_diff(start_count, prob, try_pick)
    
    prob_of_picked[0] = never_picked_value
    prob_of_picked[1] = 1 - never_picked_value

    min_required_trial_prob = get_prob_try_interval_diff(start_count, prob)
    return prob_of_picked, min_required_trial_prob

def FUNC_2(start_count, prob, try_pick):
    print(prob_by_interval_diff_item(start_count, prob, try_pick))

#table1, table2 = prob_by_interval_diff_item(100, 0.1, 110)

# 5. 컴플리트 가챠(쿠폰수집가 문제)
# - 특징, 모든 재료를 다 뽑아야함
# - 사실상 모두 모으기 전에는 의미 없음
# (포켓몬 빵 같은 것임)
# - 포켓몬 빵은 고정 확률이라 계산하기 쉽지만, 실제 환경에서는 아이템마다 확률이 다르다..... 수식적인 계산은 어렵다. 몬테카를로 근사를 통한 접근방식을 이용할 것이다.
# - 하나의 컬랙션을 환성하면 끝나는 환경이므로, 복원추출에서 사용했던, n회 시행시 뽑히는 횟수별 확률 표는 제공하지 않는다.
# - 웹페이지에 작성해야 하는 정보("본 확률은 몬테카를로 근사를 사용하여 계산되었기 때문에, 확률 값의 같은 입력이어도, 결과 값이 다를 수 있습니다")

# input:
# - 모아야 하는 쿠폰 개수(모두 다른 쿠폰으로 가정), 개수가 커지면 계산시간이 오래걸림... 아이템 최대 개수 제한이 필요함(최대 100개 정도가 적당할 듯 보임)
# - 확률(쿠폰 별 확률)


# output
# - 시도 횟수별로 뽑히는 확률 값 계산(x축 시행 횟수 y축 누적확률)



# 쿠폰 수집가 문제



# https://rayc20.tistory.com/110
# https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=altawant&logNo=221307745465
# http://aispiration.com/r-algorithm/r-coupon-collector-problem.html


import random
import math
from collections import Counter

def complete_gacha(all_coupon_count, prob_list):
    count_list =[]
    count_cum_prob = {}

    
    for i in range(10000):
        count = 0
        item_list =[]
        while True:
            val = random.choices([i for i in range(all_coupon_count)], weights=prob_list, k=1)
            item_list.append(val[0])
            item_list = list(set(item_list))
            count += 1
            if len(item_list) == all_coupon_count:
                count_list.append(count)
                break
    
    counter_count = Counter(count_list)
    max_count = max(counter_count.keys())
    all_count = sum(counter_count.values())

    cum_count = 0
    for i in range(max_count+1):
        cum_count += counter_count[i]
        count_cum_prob[i] = cum_count/all_count

    return count_cum_prob

def FUNC_1(all_coupon_count, prob_list):
    print(complete_gacha(all_coupon_count, prob_list))

# complete_gacha(10,[0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1])

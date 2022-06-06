{
  "nbformat": 4,
  "nbformat_minor": 0,
  "metadata": {
    "colab": {
      "name": "확률계산.ipynb",
      "provenance": [],
      "toc_visible": true,
      "collapsed_sections": []
    },
    "kernelspec": {
      "name": "python3",
      "display_name": "Python 3"
    },
    "language_info": {
      "name": "python"
    }
  },
  "cells": [
    {
      "cell_type": "markdown",
      "source": [
        "전반적 함수 정의\n",
        "\n"
      ],
      "metadata": {
        "id": "75up8xuZfsv4"
      }
    },
    {
      "cell_type": "markdown",
      "source": [
        "# 1. 복원추출\n",
        "\n",
        "특징: 아이템을 반복적으로 무한히 뽑을 수 있음\n",
        "\n",
        "input: \n",
        "- 확률\n",
        "- 시도 횟수\n",
        "\n",
        "ex)    \n",
        "확률 -> 10%     \n",
        "시도 횟수 -> 2\n",
        "\n",
        "\n",
        "\n",
        "\n",
        "output:\n",
        "- 시도 횟수별 뽑기 확률\n",
        "- 시행횟수 별 기대확률\n",
        "\n",
        "\n"
      ],
      "metadata": {
        "id": "WYKhzksdbIFa"
      }
    },
    {
      "cell_type": "code",
      "source": [
        "from math import factorial"
      ],
      "metadata": {
        "id": "qJRHdoQaNkOP"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def bin_dist(n, k, p):\n",
        "    # 확률이 p일 때, n회 시행에서 k번 일어날 확률\n",
        "    nCk = factorial(n) / (factorial(k) * factorial(n - k))\n",
        "    pd = nCk * (p**k) * ((1-p)**(n-k))\n",
        "    return pd"
      ],
      "metadata": {
        "id": "lRLZMBRpNhMR"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def get_prob_try(p):\n",
        "    # 시도 횟수별 1번 이상 뽑힐 확률\n",
        "    prob_dic ={}\n",
        "    count = 0\n",
        "    while True:\n",
        "        prob = 1 - (1 - p)**count # 1 - 한 번도 안 뽑힐 확률 = 한 번 이상 뽑힐 확률\n",
        "        prob_dic[count]= prob\n",
        "        count += 1\n",
        "        if prob >= 0.99:\n",
        "            break\n",
        "    return prob_dic"
      ],
      "metadata": {
        "id": "MwjeIwKaQolZ"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "execution_count": null,
      "metadata": {
        "id": "1k3MIn26YBiZ"
      },
      "outputs": [],
      "source": [
        "def sampling_with_replacement(prob, try_count):\n",
        "    prob_of_picked ={} # 첫 번째표, 0부터 try count 까지의 확률\n",
        "    min_required_trial_prob ={} # 두 번째표\n",
        "\n",
        "    for i in range(try_count+1):\n",
        "        prob_of_picked[i] = bin_dist(try_count, i, prob)\n",
        "\n",
        "\n",
        "    min_required_trial_prob = get_prob_try(prob)\n",
        "    return prob_of_picked, min_required_trial_prob"
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table1, table2 = sampling_with_replacement(0.1,2)"
      ],
      "metadata": {
        "id": "ft2TNtSIQ69j"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "id": "60-1YB-MQ64n",
        "outputId": "bd4dbabd-fe1b-4716-bc44-fdb08b719677"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.81, 1: 0.18000000000000002, 2: 0.010000000000000002}"
            ]
          },
          "metadata": {},
          "execution_count": 6
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table2"
      ],
      "metadata": {
        "id": "AhFte4K9JzWi",
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "outputId": "a333cd33-1042-4fbc-9ee5-7eca20418253"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.0,\n",
              " 1: 0.09999999999999998,\n",
              " 2: 0.18999999999999995,\n",
              " 3: 0.2709999999999999,\n",
              " 4: 0.3439,\n",
              " 5: 0.40950999999999993,\n",
              " 6: 0.46855899999999995,\n",
              " 7: 0.5217030999999999,\n",
              " 8: 0.5695327899999999,\n",
              " 9: 0.6125795109999999,\n",
              " 10: 0.6513215599,\n",
              " 11: 0.6861894039099999,\n",
              " 12: 0.7175704635189999,\n",
              " 13: 0.7458134171670999,\n",
              " 14: 0.7712320754503899,\n",
              " 15: 0.794108867905351,\n",
              " 16: 0.8146979811148158,\n",
              " 17: 0.8332281830033342,\n",
              " 18: 0.8499053647030008,\n",
              " 19: 0.8649148282327007,\n",
              " 20: 0.8784233454094307,\n",
              " 21: 0.8905810108684876,\n",
              " 22: 0.9015229097816388,\n",
              " 23: 0.9113706188034749,\n",
              " 24: 0.9202335569231275,\n",
              " 25: 0.9282102012308147,\n",
              " 26: 0.9353891811077333,\n",
              " 27: 0.94185026299696,\n",
              " 28: 0.9476652366972639,\n",
              " 29: 0.9528987130275375,\n",
              " 30: 0.9576088417247838,\n",
              " 31: 0.9618479575523053,\n",
              " 32: 0.9656631617970749,\n",
              " 33: 0.9690968456173674,\n",
              " 34: 0.9721871610556306,\n",
              " 35: 0.9749684449500675,\n",
              " 36: 0.9774716004550608,\n",
              " 37: 0.9797244404095548,\n",
              " 38: 0.9817519963685992,\n",
              " 39: 0.9835767967317394,\n",
              " 40: 0.9852191170585654,\n",
              " 41: 0.9866972053527089,\n",
              " 42: 0.988027484817438,\n",
              " 43: 0.9892247363356942,\n",
              " 44: 0.9903022627021247}"
            ]
          },
          "metadata": {},
          "execution_count": 7
        }
      ]
    },
    {
      "cell_type": "markdown",
      "source": [
        "# 2. 복원 추출 천장시스템(이전에 작성한 이름은 비복원 추출(잘못된 네이밍인듯함))\n",
        "특징: \n",
        "- 뽑을 수 있는 최대 아이템의 개수를 1개로 한정함(한번도 안뽑히거나, 뽑히거나)\n",
        "- 천장 시스템을 고려한다\n",
        "\n",
        "\n",
        "복원추출과 차이점:\n",
        "- 최대 추출 횟수에 도달하면 무조건 뽑힌다.\n",
        "\n",
        "input: \n",
        "- 확률\n",
        "- 시도 횟수(최대 시도 횟수보다 클 수 없다. input 받을 때 확인 필요)\n",
        "- 최대 시도 횟수\n",
        "\n",
        "ex)    \n",
        "확률 -> 10%     \n",
        "시도 횟수 -> 2    \n",
        "최대 시도 횟수 -> 10(10회에는 무조건 뽑힘)\n",
        "\n",
        "\n",
        "\n",
        "output:\n",
        "- 시도 횟수별 뽑기 확률\n",
        "- 시행횟수 별 기대확률\n",
        "\n",
        "\n",
        "    \n"
      ],
      "metadata": {
        "id": "SL66pCskbITU"
      }
    },
    {
      "cell_type": "code",
      "source": [
        "from math import factorial"
      ],
      "metadata": {
        "id": "OVOvqVxq4rSx"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def bin_dist(n, k, p):\n",
        "    # 확률이 p일 때, n회 시행에서 k번 일어날 확률\n",
        "    nCk = factorial(n) / (factorial(k) * factorial(n - k))\n",
        "    pd = nCk * (p**k) * ((1-p)**(n-k))\n",
        "    return pd"
      ],
      "metadata": {
        "id": "a8unNpfE4rP-"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def get_prob_try_ceiling(p, limit):\n",
        "    # 시도 횟수별 1번 이상 뽑힐 확률\n",
        "    prob_dic ={}\n",
        "    count = 0\n",
        "    while True:\n",
        "        prob = 1 - (1 - p)**count # 1 - 한 번도 안 뽑힐 확률 = 한 번 이상 뽑힐 확률\n",
        "        prob_dic[count]= prob\n",
        "        count += 1\n",
        "        if count == limit:\n",
        "            prob_dic[limit] = 1\n",
        "            break\n",
        "    return prob_dic"
      ],
      "metadata": {
        "id": "Eeh4KojG4rNZ"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def sampling_with_replacement_ceiling(prob, try_count, limit):\n",
        "    prob_of_picked ={} # 첫 번째표, 0부터 try count 까지의 확률\n",
        "    min_required_trial_prob ={} # 두 번째표\n",
        "    \n",
        "    none_prob = bin_dist(try_count, 0, prob)\n",
        "    prob_of_picked[0] = none_prob\n",
        "    prob_of_picked[1] = 1 - none_prob\n",
        "\n",
        "    min_required_trial_prob = get_prob_try_ceiling(prob, limit)\n",
        "    return prob_of_picked, min_required_trial_prob"
      ],
      "metadata": {
        "id": "1-uT87ZF4rG1"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1, table2 = sampling_with_replacement_ceiling(0.1,2,20)"
      ],
      "metadata": {
        "id": "4c17sJgy4rEt"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "id": "0aH6FNIWS2Il",
        "outputId": "48fa7f34-3ae0-4d5c-eec5-4970abc90b74"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.81, 1: 0.18999999999999995}"
            ]
          },
          "metadata": {},
          "execution_count": 13
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table2"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "id": "jVGOMQfOS2DB",
        "outputId": "8d6f6aa0-8e0c-4678-f62c-cbfb5bb1b58a"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.0,\n",
              " 1: 0.09999999999999998,\n",
              " 2: 0.18999999999999995,\n",
              " 3: 0.2709999999999999,\n",
              " 4: 0.3439,\n",
              " 5: 0.40950999999999993,\n",
              " 6: 0.46855899999999995,\n",
              " 7: 0.5217030999999999,\n",
              " 8: 0.5695327899999999,\n",
              " 9: 0.6125795109999999,\n",
              " 10: 0.6513215599,\n",
              " 11: 0.6861894039099999,\n",
              " 12: 0.7175704635189999,\n",
              " 13: 0.7458134171670999,\n",
              " 14: 0.7712320754503899,\n",
              " 15: 0.794108867905351,\n",
              " 16: 0.8146979811148158,\n",
              " 17: 0.8332281830033342,\n",
              " 18: 0.8499053647030008,\n",
              " 19: 0.8649148282327007,\n",
              " 20: 1}"
            ]
          },
          "metadata": {},
          "execution_count": 14
        }
      ]
    },
    {
      "cell_type": "markdown",
      "source": [
        "# 3. 구간별 확률(동일 아이템, 이전에 조건부 확률로 이름 붙였었음)\n",
        "\n",
        "특징:\n",
        "- 뽑는 횟수에 따라 확률 변동이 있다.(뽑을 수 있는 아이템이 같다.)\n",
        "- 시도 횟수별 뽑기 확률, 적당한 이산확률 분포식을 계산하지 못함\n",
        "- 구간에 시도횟수가 충분하지 않을 경우 99%와 같이 높은 확률의 시도 횟수가 그래프 입력으로 주어지지 않을 수도 있음\n",
        "\n",
        "\n",
        "입력\n",
        "- 구간 리스트 값\n",
        "[[1,10],[11,20],[21,30]]\n",
        "- 구간별 확률 리스트 값\n",
        "[5, 10, 20] <- 퍼센트 값\n",
        "- 시도 횟수\n",
        "\n",
        "output:\n",
        "- 시도 횟수별 뽑기 확률(한 번도 안뽑힐 확률, 1번 뽑힐 확률)\n",
        "- 시행횟수 별 기대확률\n",
        "\n",
        "\n",
        "\n"
      ],
      "metadata": {
        "id": "gdNtLJjJfK_J"
      }
    },
    {
      "cell_type": "code",
      "source": [
        "from math import factorial"
      ],
      "metadata": {
        "id": "9QnrNPPE43C4"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def never_picked_same(range_list, prob_list, try_pick):\n",
        "    p = 1\n",
        "    for range_t, prob in zip(range_list, prob_list):\n",
        "        if try_pick > range_t[1]:\n",
        "            p = p*(1-prob)**(range_t[1] - range_t[0] + 1)\n",
        "        else:\n",
        "            p = p *(1-prob)**(try_pick - range_t[0] + 1)\n",
        "            break\n",
        "    return p"
      ],
      "metadata": {
        "id": "dMUv418xGFPI"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def get_prob_try_interval_same(range_list, prob_list):\n",
        "    # 시도 횟수별 1번 이상 뽑힐 확률\n",
        "    prob_dic ={}\n",
        "    count = 1\n",
        "    p_no = 1\n",
        "    for range_s , prob in zip(range_list, prob_list):\n",
        "        for _ in range(range_s[0], range_s[1]+1):\n",
        "            p_no = p_no * (1 - prob)\n",
        "            prob_dic[count]= 1 - p_no\n",
        "            count += 1\n",
        "            if 1 - p_no >= 0.99:\n",
        "                break\n",
        "    return prob_dic"
      ],
      "metadata": {
        "id": "EFtbTR-E43C5"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "execution_count": null,
      "metadata": {
        "id": "Syx2Mwt043C5"
      },
      "outputs": [],
      "source": [
        "def prob_by_interval_same_item(range_list, prob_list, try_pick):\n",
        "    prob_of_picked ={} # 한 번도 안 뽑힐 확률\n",
        "    min_required_trial_prob ={} # 두 번째표\n",
        "    never_pick_p = never_picked_same(range_list, prob_list, try_pick)\n",
        "    prob_of_picked[0] = never_pick_p\n",
        "    prob_of_picked[1] = 1 - never_pick_p\n",
        "    min_required_trial_prob = get_prob_try_interval_same(range_list, prob_list)\n",
        "    return prob_of_picked, min_required_trial_prob"
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "range_list =[[1,10],[11,20],[21,30]]\n",
        "prob_list =[0.05, 0.1, 0.2]"
      ],
      "metadata": {
        "id": "PX06jbgNMRn8"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1, table2 = prob_by_interval_same_item(range_list,prob_list, 13)"
      ],
      "metadata": {
        "id": "V_vZ-C-l43C6"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "outputId": "d57bd19f-c12a-40ce-a6d5-7fbec425a6a4",
        "id": "hSisvBvt43C6"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.4364792287047781, 1: 0.5635207712952219}"
            ]
          },
          "metadata": {},
          "execution_count": 21
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table2"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "outputId": "7700b34a-1132-4dd4-e93b-6610d7398676",
        "id": "Z5YpjPZH43C6"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{1: 0.050000000000000044,\n",
              " 2: 0.09750000000000003,\n",
              " 3: 0.1426250000000001,\n",
              " 4: 0.18549375000000012,\n",
              " 5: 0.22621906250000012,\n",
              " 6: 0.2649081093750002,\n",
              " 7: 0.3016627039062503,\n",
              " 8: 0.33657956871093775,\n",
              " 9: 0.3697505902753909,\n",
              " 10: 0.40126306076162144,\n",
              " 11: 0.4611367546854592,\n",
              " 12: 0.5150230792169133,\n",
              " 13: 0.5635207712952219,\n",
              " 14: 0.6071686941656997,\n",
              " 15: 0.6464518247491298,\n",
              " 16: 0.6818066422742168,\n",
              " 17: 0.7136259780467951,\n",
              " 18: 0.7422633802421156,\n",
              " 19: 0.768037042217904,\n",
              " 20: 0.7912333379961136,\n",
              " 21: 0.8329866703968909,\n",
              " 22: 0.8663893363175127,\n",
              " 23: 0.8931114690540102,\n",
              " 24: 0.914489175243208,\n",
              " 25: 0.9315913401945665,\n",
              " 26: 0.9452730721556531,\n",
              " 27: 0.9562184577245225,\n",
              " 28: 0.9649747661796181,\n",
              " 29: 0.9719798129436944,\n",
              " 30: 0.9775838503549555}"
            ]
          },
          "metadata": {},
          "execution_count": 22
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        ""
      ],
      "metadata": {
        "id": "TpqutP704y0-"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "markdown",
      "source": [
        "# 4. 구간별 확률\n",
        "\n",
        "특징:\n",
        "- 뽑는 횟수 별로 확률 값이 다름(뽑을 수 있는 아이템이 다름)\n",
        "- 일반적으로 아래쪽 확률에서는 아이템이 안뽑힘(처음에는 잡템만 뽑힘, 나중에만 좋은 아이템을 뽑을 수 있음)\n",
        "- 이 경우에도 target아이템 하나만 뽑으면 된다. 따라서 뽑힌 횟수별 확률그래프 불필요\n",
        "\n",
        "\n",
        "\n",
        "입력\n",
        "- 아이템을 확득이 가능한 시작 횟수(ex, 200회 뽑기이후 부터 획득 가능)\n",
        "- 해당 구간에서 뽑히는 확률    \n",
        "- 시도 횟수(아이템 획득 가능한 횟수보다 더 큰 수가 입력으로 들어와야 한다.)\n",
        "\n",
        "output:\n",
        "- 시도 횟수별 뽑기 확률(한 번도 안뽑힐 확률, 1번 뽑힐 확률)(table1)\n",
        "- 시행횟수 별 기대확률\n"
      ],
      "metadata": {
        "id": "Zz6hCBc2fLIS"
      }
    },
    {
      "cell_type": "code",
      "source": [
        "def never_picked_diff(start_count, prob, try_pick):\n",
        "    p = 1\n",
        "    if start_count > try_pick:\n",
        "        return p\n",
        "    else:\n",
        "        p = p * (1-prob)**(try_pick - start_count + 1)\n",
        "        return p"
      ],
      "metadata": {
        "id": "036dJfXHQFI4"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def get_prob_try_interval_diff(start_count, prob):\n",
        "    # 시도 횟수별 1번 이상 뽑힐 확률\n",
        "    prob_dic ={}\n",
        "    count = start_count\n",
        "    p_no = 1\n",
        "\n",
        "    for i in range(start_count):\n",
        "        prob_dic[i] = 0\n",
        "    \n",
        "    while True:\n",
        "        p_no = p_no * (1 - prob)\n",
        "        prob_dic[count]= 1 - p_no\n",
        "        count += 1\n",
        "\n",
        "        if 1 - p_no >= 0.99:\n",
        "            break\n",
        "\n",
        "    return prob_dic"
      ],
      "metadata": {
        "id": "hlm5W_YcQFI4"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "execution_count": null,
      "metadata": {
        "id": "v5gsJaWkQFI4"
      },
      "outputs": [],
      "source": [
        "def prob_by_interval_diff_item(start_count, prob, try_pick):\n",
        "    prob_of_picked ={} # 한 번도 안 뽑힐 확률\n",
        "    min_required_trial_prob ={} # 두 번째표\n",
        "    never_picked_value = never_picked_diff(start_count, prob, try_pick)\n",
        "    \n",
        "    prob_of_picked[0] = never_picked_value\n",
        "    prob_of_picked[1] = 1 - never_picked_value\n",
        "\n",
        "    min_required_trial_prob = get_prob_try_interval_diff(start_count, prob)\n",
        "    return prob_of_picked, min_required_trial_prob"
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table1, table2 = prob_by_interval_diff_item(100, 0.1, 110)"
      ],
      "metadata": {
        "id": "B9HCvlTbQFI4"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "table1"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "outputId": "572750ce-c7d1-48d3-b2ad-5a1e1e2c8c3d",
        "id": "4loyrqfxQFI5"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.31381059609000006, 1: 0.6861894039099999}"
            ]
          },
          "metadata": {},
          "execution_count": 27
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        "table2"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "outputId": "205861da-8a72-42e2-9ba2-cc3d5e8a393e",
        "id": "UOc4ddpnQFI5"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0,\n",
              " 1: 0,\n",
              " 2: 0,\n",
              " 3: 0,\n",
              " 4: 0,\n",
              " 5: 0,\n",
              " 6: 0,\n",
              " 7: 0,\n",
              " 8: 0,\n",
              " 9: 0,\n",
              " 10: 0,\n",
              " 11: 0,\n",
              " 12: 0,\n",
              " 13: 0,\n",
              " 14: 0,\n",
              " 15: 0,\n",
              " 16: 0,\n",
              " 17: 0,\n",
              " 18: 0,\n",
              " 19: 0,\n",
              " 20: 0,\n",
              " 21: 0,\n",
              " 22: 0,\n",
              " 23: 0,\n",
              " 24: 0,\n",
              " 25: 0,\n",
              " 26: 0,\n",
              " 27: 0,\n",
              " 28: 0,\n",
              " 29: 0,\n",
              " 30: 0,\n",
              " 31: 0,\n",
              " 32: 0,\n",
              " 33: 0,\n",
              " 34: 0,\n",
              " 35: 0,\n",
              " 36: 0,\n",
              " 37: 0,\n",
              " 38: 0,\n",
              " 39: 0,\n",
              " 40: 0,\n",
              " 41: 0,\n",
              " 42: 0,\n",
              " 43: 0,\n",
              " 44: 0,\n",
              " 45: 0,\n",
              " 46: 0,\n",
              " 47: 0,\n",
              " 48: 0,\n",
              " 49: 0,\n",
              " 50: 0,\n",
              " 51: 0,\n",
              " 52: 0,\n",
              " 53: 0,\n",
              " 54: 0,\n",
              " 55: 0,\n",
              " 56: 0,\n",
              " 57: 0,\n",
              " 58: 0,\n",
              " 59: 0,\n",
              " 60: 0,\n",
              " 61: 0,\n",
              " 62: 0,\n",
              " 63: 0,\n",
              " 64: 0,\n",
              " 65: 0,\n",
              " 66: 0,\n",
              " 67: 0,\n",
              " 68: 0,\n",
              " 69: 0,\n",
              " 70: 0,\n",
              " 71: 0,\n",
              " 72: 0,\n",
              " 73: 0,\n",
              " 74: 0,\n",
              " 75: 0,\n",
              " 76: 0,\n",
              " 77: 0,\n",
              " 78: 0,\n",
              " 79: 0,\n",
              " 80: 0,\n",
              " 81: 0,\n",
              " 82: 0,\n",
              " 83: 0,\n",
              " 84: 0,\n",
              " 85: 0,\n",
              " 86: 0,\n",
              " 87: 0,\n",
              " 88: 0,\n",
              " 89: 0,\n",
              " 90: 0,\n",
              " 91: 0,\n",
              " 92: 0,\n",
              " 93: 0,\n",
              " 94: 0,\n",
              " 95: 0,\n",
              " 96: 0,\n",
              " 97: 0,\n",
              " 98: 0,\n",
              " 99: 0,\n",
              " 100: 0.09999999999999998,\n",
              " 101: 0.18999999999999995,\n",
              " 102: 0.2709999999999999,\n",
              " 103: 0.3438999999999999,\n",
              " 104: 0.4095099999999998,\n",
              " 105: 0.46855899999999984,\n",
              " 106: 0.5217030999999999,\n",
              " 107: 0.5695327899999998,\n",
              " 108: 0.6125795109999999,\n",
              " 109: 0.6513215598999998,\n",
              " 110: 0.6861894039099998,\n",
              " 111: 0.7175704635189999,\n",
              " 112: 0.7458134171670998,\n",
              " 113: 0.7712320754503899,\n",
              " 114: 0.794108867905351,\n",
              " 115: 0.8146979811148158,\n",
              " 116: 0.8332281830033342,\n",
              " 117: 0.8499053647030008,\n",
              " 118: 0.8649148282327007,\n",
              " 119: 0.8784233454094307,\n",
              " 120: 0.8905810108684875,\n",
              " 121: 0.9015229097816388,\n",
              " 122: 0.9113706188034749,\n",
              " 123: 0.9202335569231275,\n",
              " 124: 0.9282102012308147,\n",
              " 125: 0.9353891811077332,\n",
              " 126: 0.9418502629969598,\n",
              " 127: 0.9476652366972639,\n",
              " 128: 0.9528987130275375,\n",
              " 129: 0.9576088417247838,\n",
              " 130: 0.9618479575523053,\n",
              " 131: 0.9656631617970748,\n",
              " 132: 0.9690968456173673,\n",
              " 133: 0.9721871610556306,\n",
              " 134: 0.9749684449500675,\n",
              " 135: 0.9774716004550608,\n",
              " 136: 0.9797244404095548,\n",
              " 137: 0.9817519963685992,\n",
              " 138: 0.9835767967317394,\n",
              " 139: 0.9852191170585654,\n",
              " 140: 0.9866972053527089,\n",
              " 141: 0.988027484817438,\n",
              " 142: 0.9892247363356942,\n",
              " 143: 0.9903022627021247}"
            ]
          },
          "metadata": {},
          "execution_count": 28
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        ""
      ],
      "metadata": {
        "id": "w5bjFqIwcpia"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "markdown",
      "source": [
        "# 5. 컴플리트 가챠(쿠폰수집가 문제)\n",
        "- 특징, 모든 재료를 다 뽑아야함\n",
        "- 사실상 모두 모으기 전에는 의미 없음\n",
        "(포켓몬 빵 같은 것임)\n",
        "- 포켓몬 빵은 고정 확률이라 계산하기 쉽지만, 실제 환경에서는 아이템마다 확률이 다르다..... 수식적인 계산은 어렵다. 몬테카를로 근사를 통한 접근방식을 이용할 것이다.\n",
        "- 하나의 컬랙션을 환성하면 끝나는 환경이므로, 복원추출에서 사용했던, n회 시행시 뽑히는 횟수별 확률 표는 제공하지 않는다.\n",
        "- 웹페이지에 작성해야 하는 정보(\"본 확률은 몬테카를로 근사를 사용하여 계산되었기 때문에, 확률 값의 같은 입력이어도, 결과 값이 다를 수 있습니다\")\n",
        "\n",
        "input:\n",
        "- 모아야 하는 쿠폰 개수(모두 다른 쿠폰으로 가정), 개수가 커지면 계산시간이 오래걸림... 아이템 최대 개수 제한이 필요함(최대 100개 정도가 적당할 듯 보임)\n",
        "- 확률(쿠폰 별 확률)\n",
        "\n",
        "\n",
        "output\n",
        "- 시도 횟수별로 뽑히는 확률 값 계산(x축 시행 횟수 y축 누적확률)\n",
        "\n",
        "\n",
        "\n",
        "쿠폰 수집가 문제\n",
        "\n",
        "\n",
        "\n",
        "https://rayc20.tistory.com/110\n",
        "https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=altawant&logNo=221307745465\n",
        "http://aispiration.com/r-algorithm/r-coupon-collector-problem.html\n",
        "\n"
      ],
      "metadata": {
        "id": "DLonQCOBfLPq"
      }
    },
    {
      "cell_type": "code",
      "source": [
        "import random\n",
        "import math\n",
        "from collections import Counter"
      ],
      "metadata": {
        "id": "s7ZOsAtQAlM7"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "def complete_gacha(all_coupon_count, prob_list):\n",
        "    count_list =[]\n",
        "    count_cum_prob = {}\n",
        "\n",
        "    \n",
        "    for i in range(10000):\n",
        "        count = 0\n",
        "        item_list =[]\n",
        "        while True:\n",
        "            val = random.choices([i for i in range(all_coupon_count)], weights=prob_list, k=1)\n",
        "            item_list.append(val[0])\n",
        "            item_list = list(set(item_list))\n",
        "            count += 1\n",
        "            if len(item_list) == all_coupon_count:\n",
        "                count_list.append(count)\n",
        "                break\n",
        "    \n",
        "    counter_count = Counter(count_list)\n",
        "    max_count = max(counter_count.keys())\n",
        "    all_count = sum(counter_count.values())\n",
        "\n",
        "    cum_count = 0\n",
        "    for i in range(max_count+1):\n",
        "        cum_count += counter_count[i]\n",
        "        count_cum_prob[i] = cum_count/all_count\n",
        "\n",
        "    return count_cum_prob"
      ],
      "metadata": {
        "id": "GePTOnSVfS5W"
      },
      "execution_count": null,
      "outputs": []
    },
    {
      "cell_type": "code",
      "source": [
        "complete_gacha(10,[0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1])"
      ],
      "metadata": {
        "colab": {
          "base_uri": "https://localhost:8080/"
        },
        "id": "dEATBgizKWvV",
        "outputId": "483dd774-11ac-4fa3-e1d9-3d7cca2de1b2"
      },
      "execution_count": null,
      "outputs": [
        {
          "output_type": "execute_result",
          "data": {
            "text/plain": [
              "{0: 0.0,\n",
              " 1: 0.0,\n",
              " 2: 0.0,\n",
              " 3: 0.0,\n",
              " 4: 0.0,\n",
              " 5: 0.0,\n",
              " 6: 0.0,\n",
              " 7: 0.0,\n",
              " 8: 0.0,\n",
              " 9: 0.0,\n",
              " 10: 0.0004,\n",
              " 11: 0.0021,\n",
              " 12: 0.0072,\n",
              " 13: 0.0141,\n",
              " 14: 0.0285,\n",
              " 15: 0.0458,\n",
              " 16: 0.0701,\n",
              " 17: 0.0988,\n",
              " 18: 0.1315,\n",
              " 19: 0.1692,\n",
              " 20: 0.2101,\n",
              " 21: 0.2537,\n",
              " 22: 0.2975,\n",
              " 23: 0.3443,\n",
              " 24: 0.3895,\n",
              " 25: 0.4325,\n",
              " 26: 0.4739,\n",
              " 27: 0.5184,\n",
              " 28: 0.559,\n",
              " 29: 0.595,\n",
              " 30: 0.6284,\n",
              " 31: 0.6592,\n",
              " 32: 0.6892,\n",
              " 33: 0.7161,\n",
              " 34: 0.7411,\n",
              " 35: 0.7635,\n",
              " 36: 0.7851,\n",
              " 37: 0.8056,\n",
              " 38: 0.8249,\n",
              " 39: 0.8419,\n",
              " 40: 0.8568,\n",
              " 41: 0.8701,\n",
              " 42: 0.8818,\n",
              " 43: 0.8922,\n",
              " 44: 0.9021,\n",
              " 45: 0.9116,\n",
              " 46: 0.9206,\n",
              " 47: 0.9268,\n",
              " 48: 0.935,\n",
              " 49: 0.9415,\n",
              " 50: 0.9469,\n",
              " 51: 0.9525,\n",
              " 52: 0.9583,\n",
              " 53: 0.9623,\n",
              " 54: 0.9667,\n",
              " 55: 0.9704,\n",
              " 56: 0.9727,\n",
              " 57: 0.9758,\n",
              " 58: 0.9782,\n",
              " 59: 0.9797,\n",
              " 60: 0.9817,\n",
              " 61: 0.9837,\n",
              " 62: 0.9854,\n",
              " 63: 0.9863,\n",
              " 64: 0.9871,\n",
              " 65: 0.9877,\n",
              " 66: 0.9883,\n",
              " 67: 0.9895,\n",
              " 68: 0.9908,\n",
              " 69: 0.9915,\n",
              " 70: 0.9928,\n",
              " 71: 0.9934,\n",
              " 72: 0.9941,\n",
              " 73: 0.995,\n",
              " 74: 0.9951,\n",
              " 75: 0.9955,\n",
              " 76: 0.996,\n",
              " 77: 0.9965,\n",
              " 78: 0.9968,\n",
              " 79: 0.9971,\n",
              " 80: 0.9977,\n",
              " 81: 0.998,\n",
              " 82: 0.9981,\n",
              " 83: 0.9983,\n",
              " 84: 0.9984,\n",
              " 85: 0.9987,\n",
              " 86: 0.9989,\n",
              " 87: 0.9993,\n",
              " 88: 0.9993,\n",
              " 89: 0.9994,\n",
              " 90: 0.9996,\n",
              " 91: 0.9997,\n",
              " 92: 0.9997,\n",
              " 93: 0.9998,\n",
              " 94: 0.9998,\n",
              " 95: 0.9998,\n",
              " 96: 0.9998,\n",
              " 97: 0.9998,\n",
              " 98: 0.9998,\n",
              " 99: 0.9998,\n",
              " 100: 0.9998,\n",
              " 101: 0.9998,\n",
              " 102: 0.9998,\n",
              " 103: 0.9999,\n",
              " 104: 0.9999,\n",
              " 105: 0.9999,\n",
              " 106: 0.9999,\n",
              " 107: 1.0}"
            ]
          },
          "metadata": {},
          "execution_count": 31
        }
      ]
    },
    {
      "cell_type": "code",
      "source": [
        ""
      ],
      "metadata": {
        "id": "pfFZlCjVllhA"
      },
      "execution_count": null,
      "outputs": []
    }
  ]
}
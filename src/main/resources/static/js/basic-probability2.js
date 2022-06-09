// 3동일 아이템 구간별 확률, 2다른 아이템 구간별 확률 Handles functionality of Probability
$(window).load(function () {
    chance();
});

function chance() {
    // Input probs
    var inputProb = JSON.parse($("#inputprobcsv").val());
    var gacha_id = $("#gacha_id").attr("value");
    var itemname = $("#itemname").attr("value");

    console.log(inputProb);

    //Constants
    var probTheo = [0, 0];
    var range = [];
    var range_prob = [];
    var start_num;
    var new_prob;
    var name = itemname; // 전달[DONE] (아이템 이름)

    if (gacha_id == 3) {
        // 동일 아이템 구간별 확률
        array = [inputProb.startCnts, inputProb.endCnts];
        // merge two array to one 2d array
        range = array.reduce(function (r, a, i) {
          a.forEach(function(b, j) {
            r[j] = r[j] || [];
            r[j][i] = b;
          });
          return r;
        }, []); // 전달[DONE] ([구간 시작(이상), 구간 끝(이하)]를 리스트 형태로)
        
        inputProb.itemProbs.forEach((e, i) => {inputProb.itemProbs[i] = e * 0.01});
        range_prob = inputProb.itemProbs; // 전달[DONE] (각 범위 별 확률)

        console.log(range);
        console.log(range_prob);
    }
    if (gacha_id == 2) {
        // 다른 아이템 구간별 확률
        start_num = inputProb.startCnt; // 전달[DONE] (아이템이 뽑히기 시작하는 구간(이상))
        new_prob = inputProb.itemProb * 0.01; // 전달[DONE] (확률)
    }

    var countCoin = [0, 0];
    var coinData = [
        {
            data: [
                {
                    value: countCoin[0],
                    side: "head"
                }, {
                    value: countCoin[1],
                    side: "tail"
                }
            ],
            state: "Observed outcomes"
        }
    ];
    var total_cnt = 0,
        interval,
        draws = 1,
        flag = true,
        i = 0;

    var avail_money = document.getElementById("avail_money");
    var item_cost = document.getElementById("item_cost");
    var left_money = document.getElementById("left_money");
    var total_try = document.getElementById("total_try");
    avail_money.onchange = function () {
        left_money.innerText = avail_money.value;
    };

    var margin = {
        top: 15,
        right: 5,
        bottom: 15,
        left: 5
    };
    var width = 800;
    height = 500;

    //Create SVG
    var svgCoin = d3
        .select("#barCoin")
        .append("svg")
        .attr("width", "100%")
        .attr("height", "100%")
        .attr("viewBox", "0 0 " + (
            width + margin.left + margin.right
        ) + " " + (
            height + margin.top + margin.bottom
        ))
        .attr("preserveAspectRatio", "xMidYMid meet");

    //Create Container
    var containerCoin = svgCoin.append("g");

    //Create Scales
    var yScaleCoin = d3
        .scale
        .linear()
        .domain([0, 1]);
    var x0ScaleCoin = d3
        .scale
        .ordinal()
        .domain(["Observed outcomes"]);
    var x1ScaleCoin = d3
        .scale
        .ordinal()
        .domain(["head", "tail"]);

    //Create SVG Elements
    var states = containerCoin
        .selectAll("g.state")
        .data(coinData)
        .enter()
        .append("g")
        .attr("class", "state");

    var rects = states
        .selectAll("rect")
        .data(function (d) {
            return d.data;
        })
        .enter()
        .append("rect");

    var sides = states
        .selectAll("image")
        .data(function (d) {
            return d.data;
        })
        .enter()
        .append("image")
        .attr("class", "coin");

    var axisCoin = svgCoin
        .append("g")
        .attr("class", "x axis");

    var xAxisCoin = d3
        .svg
        .axis()
        .scale(x0ScaleCoin)
        .orient("bottom")
        .ticks(0);

    //Create tool tips for observed and expected
    var tipCoinObs = d3
        .tip()
        .attr("id", "tipCoinObs")
        .attr("class", "d3-tip")
        .offset([-10, 0]);

    //Update rectangles and text
    function updateCoin(t) {
        var total = Math.max(1, countCoin[0] + countCoin[1]);
        var probObs = [
            countCoin[0] / total,
            countCoin[1] / total
        ];
        coinData[0]
            .data[0]
            .value = probObs[0];
        coinData[0]
            .data[1]
            .value = probObs[1];

        tipCoinObs.html(function (d) {
            return (round(d.value, 2) + " = " + round(d.value * total, 0) + "/" + total);
        });

        states
            .attr("transform", function (d) {
                return "translate(" + x0ScaleCoin(d.state) + "," + 0 + ")";
            })
            .attr("class", function (d) {
                return d.state;
            });

        rects
            .transition()
            .duration(t)
            .attr("width", x1ScaleCoin.rangeBand())
            .attr("x", function (d) {
                return x1ScaleCoin(d.side);
            })
            .attr("y", function (d) {
                return yScaleCoin(d.value);
            })
            .attr("height", function (d) {
                return yScaleCoin(1 - d.value);
            })
            .attr("class", function (d) {
                return d.side;
            });

        sides
            .attr("xlink:href", function (d) {
                return "/assets/img/" + d.side + ".png";
            })
            .attr("x", function (d) {
                return (
                    x1ScaleCoin(d.side) + x1ScaleCoin.rangeBand() / 2 - (x1ScaleCoin.rangeBand() * 2) / 10
                );
            })
            .attr("y", function (d) {
                return yScaleCoin(0) + 20;
            })
            .attr("width", (x1ScaleCoin.rangeBand() * 2) / 5)
            .attr("height", (x1ScaleCoin.rangeBand() * 2) / 5);

        containerCoin
            .selectAll("g.Observed rect")
            .each(function () {
                d3
                    .select(this)
                    .on("mouseover", tipCoinObs.show)
                    .on("mouseout", tipCoinObs.hide);
            });
    }

    function update() {
        var num = Math.random();
        if (num < probTheo[0]) {
            countCoin[0] = countCoin[0] + 1;
        } else {
            countCoin[1] = countCoin[1] + 1;
        }

        total_try.innerText = total_cnt;
        left_money.innerText = left_money.innerText - item_cost.value;

        if (flag && check_pop()) 
            pop(name + " 아이템이 뽑힐 때 까지 시도횟수 ");
        
        updateCoin(100);
    }

    function check_pop() {
        if (countCoin[0] > 0) 
            return true;
        else 
            return false;
        }
    
    function pop(string) {
        flag = false;

        var total_cost = total_cnt * item_cost.value;

        document
            .getElementById("success_test")
            .innerText = string + String(total_cnt) + "회, 총 금액 " + String(total_cost) + "원 " +
                    "소비되었습니다.";
    }

    function start_sampling() {
        var cnt = 0;
        interval = setInterval(function () {
            cnt++;
            total_cnt++;

            if (gacha_id == 3) {
                for (; i < range_prob.length; i++) {
                    if (total_cnt <= range[i][1]) {
                        probTheo[0] = range_prob[i];
                        probTheo[1] = 1 - probTheo[0];
                        break;
                    }
                }
            }

            if (gacha_id == 2 && total_cnt == start_num) {
                probTheo[0] = new_prob;
                probTheo[1] = 1 - probTheo[0];
            }

            update();

            if (gacha_id == 3 && total_cnt == range[range_prob.length - 1][1]) {
                if (flag) 
                    pop("천장 시스템으로 시도횟수 ");
                $("#form_chance").css({color: "gray", "pointer-events": "none"});
                clearInterval(interval);
            }

            if (cnt == draws) {
                clearInterval(interval);
            }
        }, 40);
    }

    d3
        .select("#try")
        .on("input", function () {
            draws = this.value;
            d3
                .select("#try-value")
                .text(draws);
        });

    $("#form_chance").click(function () {
        clearInterval(interval);
        start_sampling();
    });

    //Update SVG based on width of container
    function drawCoin() {
        var width = d3
            .select("#barCoin")
            .node()
            .getBoundingClientRect()
            .width;
        var height = 550;
        var padCoin = 100;

        //Update SVG
        svgCoin.call(tipCoinObs);

        //Update Scales
        yScaleCoin.range([
            height - 2 * padCoin,
            0
        ]);
        x0ScaleCoin.rangeRoundBands([
            0, width
        ], 0.1);
        x1ScaleCoin.rangeRoundBands([
            0, x0ScaleCoin.rangeBand()
        ], 0.5);

        //Update Container
        containerCoin.attr("transform", "translate(" + -50 + "," + padCoin + ")");

        //Update Axis
        axisCoin
            .attr("transform", "translate(" + -50 + "," + (
                height - padCoin + 1
            ) + ")")
            .call(xAxisCoin);

        //Update Rectangles
        updateCoin(0);
    }
    drawCoin();
    $(window).on("resize", drawCoin);
}

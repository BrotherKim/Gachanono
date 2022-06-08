const gacha = {
    init: function () {
        const self = this;
        self.initModal();
        self.initUI();
    },
    initModal: function () {
        // register modal component
        Vue.component("modal", {template: "#modal-template"});

        // start app
        new Vue({
            el: "#app",
            data: {
                showModal: false
            }
        });
    },
    initUI: function () {
        const self = this;
        const mainTableName = '#layoutSidenav_content';
        const mainTable = new Vue({
            el: mainTableName,
            data: function () {
                return {
                    table: {
                        crawledProbTable: '',
                        userProbTable: ''
                    },
                    crawled: {
                        display: false
                    },
                    price: {
                        selected: ''
                    },
                    game: {
                        selected: ''
                    },
                    item: {
                        list: [
                            {
                                id: '-1',
                                itemname: '게임을 먼저 선택해주세요'
                            }
                        ],
                        selected: -1,
                        clicked: '표에서 선택해주세요',
                    },
                    gacha: {
                        selected: ''
                    },
                    message: 'Hello Vue!'
                };
            },
            mounted: function () {},
            methods: {
                GameSelected: function () {
                    var self = this;
                    $
                        .ajax({
                            type: 'GET',
                            url: '/api/prob/itemlist/' + self.game.selected,
                            dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            success: function (retval) {
                                self.item.list = retval.content;
                            }
                        })
                        .done(function () {
                            self.CreateCrawledProbTable(self.game.selected);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });
                },
                UpdateCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    //console.log(arr);
                    var barChartData = JSON.parse(arr[0].trim());
                    var areaChartData = JSON.parse(arr[1].trim());

                    this.BarChart(barChartData);
                    this.AreaChart(areaChartData);
                },
                UpdateCompleteGachaCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    //console.log(arr);
                    var areaChartData = JSON.parse(arr[0].trim());

                    this.AreaChart(areaChartData);
                },
                GachaSelected: function () {
                    var self = this;
                    self.CreateUserProbTable(self.gacha.selected);
                },
                CreateCrawledProbTable: function (gameid) {
                    var self = this;
                    const crawledTableName = '#crawledProbTable';
                    $(crawledTableName).empty();
                    this.table.crawledProbTable = new Vue({
                        el: crawledTableName,
                        data: function () {
                            return {spreadsheet: {}}
                        },
                        mounted: function () {
                            this.spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    6, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/crawled/' + gameid + '.csv',
                                csvHeaders: false,
                                editable: false,
                                onselection: this.Select
                            });
                            //Object.assign(this, spreadsheet);
                        },
                        methods: {
                            Select: function (instance, x1, y1, x2, y2, origin) {
                                var begin = jexcel.getColumnNameFromId([x1, y1]);
                                self.item.clicked = this.spreadsheet.getValue(begin);
                                self.item.id = 0;
                                console.log(self.item);
                            },
                        },
                    });
                },
                CreateUserProbTable: function (gachaid) {
                    const userTableName = '#userProbTable';
                    $(userTableName).empty();
                    this.table.userProbTable = new Vue({
                        el: userTableName,
                        mounted: function () {
                            let spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    6, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/selection/' + gachaid + '.csv',
                                csvHeaders: false,
                                editable: true,
                                toolbar: [
                                    {
                                        type: 'i',
                                        content: 'undo',
                                        onclick: function () {
                                            spreadsheet.undo();
                                        }
                                    }, {
                                        type: 'i',
                                        content: 'redo',
                                        onclick: function () {
                                            spreadsheet.redo();
                                        }
                                    }, {
                                        type: 'i',
                                        content: 'save',
                                        onclick: function () {
                                            spreadsheet.download();
                                        }
                                    }, {
                                        type: 'select',
                                        k: 'font-family',
                                        v: ['Arial', 'Verdana']
                                    }, {
                                        type: 'select',
                                        k: 'font-size',
                                        v: [
                                            '9px',
                                            '10px',
                                            '11px',
                                            '12px',
                                            '13px',
                                            '14px',
                                            '15px',
                                            '16px',
                                            '17px',
                                            '18px',
                                            '19px',
                                            '20px'
                                        ]
                                    }, {
                                        type: 'i',
                                        content: 'format_align_left',
                                        k: 'text-align',
                                        v: 'left'
                                    }, {
                                        type: 'i',
                                        content: 'format_align_center',
                                        k: 'text-align',
                                        v: 'center'
                                    }, {
                                        type: 'i',
                                        content: 'format_align_right',
                                        k: 'text-align',
                                        v: 'right'
                                    }, {
                                        type: 'i',
                                        content: 'format_bold',
                                        k: 'font-weight',
                                        v: 'bold'
                                    }, {
                                        type: 'color',
                                        content: 'format_color_text',
                                        k: 'color'
                                    }, {
                                        type: 'color',
                                        content: 'format_color_fill',
                                        k: 'background-color'
                                    }
                                ]
                            });
                            Object.assign(this, spreadsheet);
                        }
                    });
                },
                ToggleCrawled: function () {
                    this.crawled.display = !this.crawled.display;
                },
                CompleteGacha: function () {
                    // 입력 가져오기
                    let itemCnt = this
                        .table
                        .userProbTable
                        .getColumnData(1)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let itemNames = this
                        .table
                        .userProbTable
                        .getColumnData(2)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let itemProbs = this
                        .table
                        .userProbTable
                        .getColumnData(3)
                        .filter(function (el) {
                            return 0 < el.length;
                        });

                    console.log(itemCnt);
                    console.log(itemNames);
                    console.log(itemProbs);

                    // 입력 유효성 검사
                    if (itemCnt.length != 2) {
                        alert('아이템 개수를 한개만 입력해주세요');
                        return;
                    } else {
                        itemCnt = itemCnt[1];
                    }
                    if (itemNames.length - 1 != itemCnt) {
                        alert('아이템 개수와 아이템 이름의 개수가 맞지 않습니다.');
                        return;
                    } else {
                        itemNames = itemNames.slice(1);
                    }
                    if (itemProbs.length - 1 != itemCnt) {
                        alert('아이템 개수와 아이템 확률의 개수가 맞지 않습니다.');
                        return;
                    } else {
                        itemProbs = itemProbs.slice(1);
                        for (var i = 0; i < itemProbs.length; i++) {
                            itemProbs[i] = itemProbs[i].replace(/%/g, '');
                        }
                    }

                    // 서버로 요청
                    const data = {
                        // gamename: gamename, itemname: itemname, tryprice: tryprice,
                        itemCnt: itemCnt,
                        itemNames: itemNames,
                        itemProbs: itemProbs
                    };

                    var self = this;
                    var chartData = [];
                    $
                        .ajax({
                            type: 'POST', url: '/api/calc/completegacha',
                            //dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                console.log(retval);
                                chartData = retval;
                            }
                        })
                        .done(function () {
                            self.UpdateCompleteGachaCharts(chartData);
                            self.SaveHistory(data, chartData);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                },
                SegDiff: function () {
                    // 입력 가져오기
                    let startCnt = this
                        .table
                        .userProbTable
                        .getColumnData(1)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let tryCnt = this
                        .table
                        .userProbTable
                        .getColumnData(2)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let itemProb = this
                        .table
                        .userProbTable
                        .getColumnData(3)
                        .filter(function (el) {
                            return 0 < el.length;
                        });

                    console.log(startCnt);
                    console.log(tryCnt);
                    console.log(itemProb);

                    // 입력 유효성 검사
                    if (startCnt.length != 2) {
                        alert('시작 구간은 한개만 입력해주세요');
                        return;
                    } else {
                        startCnt = startCnt[1];
                    }
                    if (tryCnt.length != 2) {
                        alert('시도 횟수는 한개만 입력해주세요');
                        return;
                    } else {
                        tryCnt = tryCnt[1];
                    }
                    if (itemProb.length != 2) {
                        alert('확률은 한개만 입력해주세요');
                        return;
                    } else {
                        itemProb = itemProb[1];
                        itemProb = itemProb.replace(/%/g, '');
                    }

                    // 서버로 요청
                    const data = {
                        // gamename: gamename, itemname: itemname, tryprice: tryprice,
                        startCnt: startCnt,
                        tryCnt: tryCnt,
                        itemProb: itemProb
                    };

                    var self = this;
                    var chartData = [];
                    $
                        .ajax({
                            type: 'POST', url: '/api/calc/segDiff',
                            //dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                console.log(retval);
                                chartData = retval;
                            }
                        })
                        .done(function () {
                            self.UpdateCharts(chartData);
                            self.SaveHistory(data, chartData);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                },
                SegSame: function () {
                    // 입력 가져오기
                    let startCnts = this
                        .table
                        .userProbTable
                        .getColumnData(1)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let endCnts = this
                        .table
                        .userProbTable
                        .getColumnData(2)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let itemProbs = this
                        .table
                        .userProbTable
                        .getColumnData(3)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let tryCnt = this
                        .table
                        .userProbTable
                        .getColumnData(4)
                        .filter(function (el) {
                            return 0 < el.length;
                        });

                    console.log(startCnts);
                    console.log(endCnts);
                    console.log(itemProbs);
                    console.log(tryCnt);

                    // 입력 유효성 검사 if (itemCnt.length != 2) {     alert('아이템 개수를 한개만 입력해주세요'); return;
                    // } else {
                    startCnts = startCnts.slice(1);
                    // } if (itemNames.length - 1 != itemCnt) {     alert('아이템 개수와 아이템 이름의 개수가 맞지
                    // 않습니다.');     return; } else {
                    endCnts = endCnts.slice(1);
                    // } if (itemProbs.length - 1 != itemCnt) {     alert('아이템 개수와 아이템 확률의 개수가 맞지
                    // 않습니다.');     return; } else {
                    itemProbs = itemProbs.slice(1);
                    for (var i = 0; i < itemProbs.length; i++) {
                        itemProbs[i] = itemProbs[i].replace(/%/g, '');
                    }

                    if (tryCnt.length != 2) {
                        alert('아이템 개수를 한개만 입력해주세요');
                        return;
                    } else {
                        tryCnt = tryCnt[1];
                    }
                    // } 서버로 요청
                    const data = {
                        // gamename: gamename, itemname: itemname, tryprice: tryprice,
                        startCnts: startCnts,
                        endCnts: endCnts,
                        itemProbs: itemProbs,
                        tryCnt: tryCnt
                    };

                    var self = this;
                    var chartData = [];
                    $
                        .ajax({
                            type: 'POST', url: '/api/calc/segSame',
                            //dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                console.log(retval);
                                chartData = retval;
                            }
                        })
                        .done(function () {
                            self.UpdateCharts(chartData);
                            self.SaveHistory(data, chartData);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                },
                SwrCeiling: function () {
                    // 입력 가져오기
                    let itemProb = this
                        .table
                        .userProbTable
                        .getColumnData(1)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let tryCnt = this
                        .table
                        .userProbTable
                        .getColumnData(2)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let maxTryCnt = this
                        .table
                        .userProbTable
                        .getColumnData(3)
                        .filter(function (el) {
                            return 0 < el.length;
                        });

                    console.log(itemProb);
                    console.log(tryCnt);
                    console.log(maxTryCnt);

                    // 입력 유효성 검사
                    if (itemProb.length != 2) {
                        alert('확률은 한개만 입력해주세요');
                        return;
                    } else {
                        itemProb = itemProb[1];
                        itemProb = itemProb.replace(/%/g, '');
                    }
                    if (tryCnt.length != 2) {
                        alert('시도 횟수는 한개만 입력해주세요');
                        return;
                    } else {
                        tryCnt = tryCnt[1];
                    }
                    if (maxTryCnt.length != 2) {
                        alert('최대 시도 횟수는 한개만 입력해주세요');
                        return;
                    } else {
                        maxTryCnt = maxTryCnt[1];
                    }

                    // 서버로 요청
                    const data = {
                        // gamename: gamename, itemname: itemname, tryprice: tryprice,
                        itemProb: itemProb,
                        tryCnt: tryCnt,
                        maxTryCnt: maxTryCnt
                    };

                    var self = this;
                    var chartData = [];
                    $
                        .ajax({
                            type: 'POST', url: '/api/calc/swrCeiling',
                            //dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                console.log(retval);
                                chartData = retval;
                            }
                        })
                        .done(function () {
                            self.UpdateCharts(chartData);
                            self.SaveHistory(data, chartData);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                },
                Swr: function () {
                    // 입력 가져오기
                    let itemProb = this
                        .table
                        .userProbTable
                        .getColumnData(1)
                        .filter(function (el) {
                            return 0 < el.length;
                        });
                    let tryCnt = this
                        .table
                        .userProbTable
                        .getColumnData(2)
                        .filter(function (el) {
                            return 0 < el.length;
                        });

                    console.log(itemProb);
                    console.log(tryCnt);

                    // 입력 유효성 검사
                    if (itemProb.length != 2) {
                        alert('아이템 개수를 한개만 입력해주세요');
                        return;
                    } else {
                        itemProb = itemProb[1];
                        itemProbs = itemProb.replace(/%/g, '');
                    }
                    if (tryCnt.length != 2) {
                        alert('아이템 개수를 한개만 입력해주세요');
                        return;
                    } else {
                        tryCnt = tryCnt[1];
                    }

                    // 서버로 요청
                    const data = {
                        // gamename: gamename, itemname: itemname, tryprice: tryprice,
                        itemProb: itemProb,
                        tryCnt: tryCnt
                    };

                    var self = this;
                    var chartData = [];
                    $
                        .ajax({
                            type: 'POST', url: '/api/calc/swr',
                            //dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                console.log(retval);
                                chartData = retval;
                            }
                        })
                        .done(function () {
                            self.UpdateCharts(chartData);
                            self.SaveHistory(data, chartData);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                },
                Calc: function () {
                    console.log('Calc');

                    // 게임 이름과 아이템 유효성검사 switch 조건문
                    let selectedGacha = this.gacha.selected;
                    switch (selectedGacha) {
                            // 컴플리트가챠
                        case '1':
                            this.CompleteGacha();
                            break;
                            // 다른 아이템 구간별 확률
                        case '2':
                            this.SegDiff();
                            break;
                            // 동일 아이템 구간별 확률
                        case '3':
                            this.SegSame();
                            break;
                            // 천장이 있는 복원추출
                        case '4':
                            this.SwrCeiling();
                            break;
                            // 복원추출
                        case '5':
                            this.Swr();
                            break;
                    }
                    // 시트에서 확률 정보 가져옴 ajax로 서버로 정보 전달 후, 계산 결과 받아옴 받아온 계산 결과 바탕으로 차트 그림
                },
                BarChart: function (barChartData) {
                    // Set new default font family and font color to mimic Bootstrap's default
                    // styling
                    $('#myBarChart').remove();
                    $('#barChartDiv').append(
                        '<canvas id="myBarChart" width="100" height="40"></canvas>'
                    );

                    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",' +
                            'Arial,sans-serif';
                    Chart.defaults.global.defaultFontColor = '#292b2c';

                    // Bar Chart Example
                    var ctx = document.getElementById("myBarChart");
                    var myLineChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: Object.keys(barChartData),
                            datasets: [
                                {
                                    label: "Revenue",
                                    backgroundColor: "rgba(2,117,216,1)",
                                    borderColor: "rgba(2,117,216,1)",
                                    data: Object.values(barChartData)
                                }
                            ]
                        },
                        options: {
                            scales: {
                                xAxes: [
                                    {
                                        gridLines: {
                                            display: false
                                        },
                                        ticks: {
                                            maxTicksLimit: 6
                                        }
                                    }
                                ],
                                yAxes: [
                                    {
                                        ticks: {
                                            min: 0,
                                            max: 1,
                                            maxTicksLimit: 5
                                        },
                                        gridLines: {
                                            display: true
                                        }
                                    }
                                ]
                            },
                            legend: {
                                display: false
                            }
                        }
                    });
                },
                AreaChart: function (areaChartData) {
                    // console.log(Object.keys(areaChartData));
                    // console.log(Object.values(areaChartData)); Set new default font family and
                    // font color to mimic Bootstrap's default styling
                    $('#myAreaChart').remove();
                    $('#areaChartDiv').append(
                        '<canvas id="myAreaChart" width="100" height="40"></canvas>'
                    );

                    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",' +
                            'Arial,sans-serif';
                    Chart.defaults.global.defaultFontColor = '#292b2c';

                    // Area Chart Example
                    var ctx = document.getElementById("myAreaChart");
                    var myLineChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: Object.keys(areaChartData),
                            datasets: [
                                {
                                    label: "Sessions",
                                    lineTension: 0.3,
                                    backgroundColor: "rgba(2,117,216,0.2)",
                                    borderColor: "rgba(2,117,216,1)",
                                    pointRadius: 5,
                                    pointBackgroundColor: "rgba(2,117,216,1)",
                                    pointBorderColor: "rgba(255,255,255,0.8)",
                                    pointHoverRadius: 5,
                                    pointHoverBackgroundColor: "rgba(2,117,216,1)",
                                    pointHitRadius: 50,
                                    pointBorderWidth: 2,
                                    data: Object.values(areaChartData)
                                }
                            ]
                        },
                        options: {
                            scales: {
                                xAxes: [
                                    {
                                        gridLines: {
                                            display: false
                                        },
                                        ticks: {
                                            maxTicksLimit: 7
                                        }
                                    }
                                ],
                                yAxes: [
                                    {
                                        ticks: {
                                            min: 0,
                                            max: 1,
                                            maxTicksLimit: 5
                                        },
                                        gridLines: {
                                            color: "rgba(0, 0, 0, .125)"
                                        }
                                    }
                                ]
                            },
                            legend: {
                                display: false
                            }
                        }
                    });

                },
                SaveHistory: function (inputprobcsvraw, outputcalcjsonraw) {
                    // console.log(typeof(inputprobcsvraw));
                    // console.log(typeof(outputcalcjsonraw));
                    // console.log(inputprobcsvraw);
                    // console.log(outputcalcjsonraw);

                    let itemname = $('#item option:selected').text().trim(); 
                    let gamename = $('#game option:selected').text().trim(); 
                    let gachaname = $('#gacha option:selected').text().trim(); 
                    let writer =  $('#writer').val();
                    let price = this.price.selected;

                    const data = {
                        title: '[' + gachaname + ']_[' + price + '원]_[' + writer + ']',
                        writer: writer,
                        inputprobcsv: JSON.stringify(inputprobcsvraw),
                        outputcalcjson: outputcalcjsonraw,
                        game_id: this.game.selected,
                        gamename: gamename,
                        item_id: this.item.selected,
                        itemname: itemname,
                        gacha_id: this.gacha.selected,
                        gachaname: gachaname,
                        price: this.price.selected,
                    };

                    // console.log(data);

                    // 공백 및 빈 문자열 체크
                    if (data.item_id == -1
                    || data.game_id.trim() === "" 
                    || data.gacha_id.trim() === ""
                    || data.price.trim() === ""
                    ) {
                        alert("공백 또는 입력하지 않은 부분이 있습니다.");
                        return false;
                    } else {
                        $
                            .ajax(
                                {type: 'POST', url: '/api/history/save', dataType: 'JSON', contentType: 'application/json; charset=utf-8', data: JSON.stringify(data)}
                            )
                            .done(function () {
                                alert('등록되었습니다.');
                                window.location.href = '/history/list';
                            })
                            .fail(function (error) {
                                alert(JSON.stringify(error));
                            });
                    }
                }
            }
        });
    }
}

gacha.init();

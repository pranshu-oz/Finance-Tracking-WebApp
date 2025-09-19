'use strict';
document.addEventListener('DOMContentLoaded', function () {

  floatchart();
});

function floatchart() {
  fetch("/transaction-by-week")
    .then(response => response.json())
    .then(data => {
      console.log(data.Income);
      document.getElementById("totalValue").insertAdjacentText("beforeend", sum(data.Income));
      var options = {
        chart: {
          height: 450,
          type: 'area',
          toolbar: {
            show: false
          }
        },
        dataLabels: {
          enabled: false
        },
        colors: ['#1890ff', '#13c2c2'],
        series: [{
          name: 'Income',
          data: data.Income
        }, {
          name: 'Expense',
          data: data.Expense
        }],
        stroke: {
          curve: 'smooth',
          width: 2
        },
        xaxis: {
          categories: data.days,
        }
      };
      var chart = new ApexCharts(document.querySelector('#visitor-chart'), options);
      chart.render();

      var options2 = {
        chart: {
          type: 'bar',
          height: 365,
          toolbar: {
            show: false
          }
        },
        colors: ['#13c2c2'],
        plotOptions: {
          bar: {
            columnWidth: '45%',
            borderRadius: 4
          }
        },
        dataLabels: {
          enabled: false
        },
        series: [{
          data: data.Income
        }],
        stroke: {
          curve: 'smooth',
          width: 2
        },
        xaxis: {
          categories: ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'],
          axisBorder: {
            show: false
          },
          axisTicks: {
            show: false
          }
        },
        yaxis: {
          show: false
        },
        grid: {
          show: false
        }
      };
      var chart = new ApexCharts(document.querySelector('#income-overview-chart'), options2);
      chart.render();

    })
    .catch(error => console.error("Error", error));

  fetch("/transaction-by-month")
    .then(response => response.json())
    .then(data => {
      var options1 = {
        chart: {
          height: 450,
          type: 'area',
          toolbar: {
            show: false
          }
        },
        dataLabels: {
          enabled: false
        },
        colors: ['#1890ff', '#13c2c2'],
        series: [{
          name: 'Income',
          data: data.Income
        }, {
          name: 'Expense',
          data: data.Expense
        }],
        stroke: {
          curve: 'smooth',
          width: 2
        },
        xaxis: {
          categories: data.Month,
        }
      };
      var chart = new ApexCharts(document.querySelector('#visitor-chart-1'), options1);
      chart.render();
    })
    .catch(error => console.error("Error", error));

  (function () {

  })();


  (function () {
    var options = {
      chart: {
        type: 'line',
        height: 340,
        toolbar: {
          show: false
        }
      },
      colors: ['#faad14'],
      plotOptions: {
        bar: {
          columnWidth: '45%',
          borderRadius: 4
        }
      },
      stroke: {
        curve: 'smooth',
        width: 1.5
      },
      grid: {
        strokeDashArray: 4
      },
      series: [{
        data: [58, 90, 38, 83, 63, 75, 35, 55]
      }],
      xaxis: {
        type: 'datetime',
        categories: [
          '2018-05-19T00:00:00.000Z',
          '2018-06-19T00:00:00.000Z',
          '2018-07-19T01:30:00.000Z',
          '2018-08-19T02:30:00.000Z',
          '2018-09-19T03:30:00.000Z',
          '2018-10-19T04:30:00.000Z',
          '2018-11-19T05:30:00.000Z',
          '2018-12-19T06:30:00.000Z'
        ],
        labels: {
          format: 'MMM'
        },
        axisBorder: {
          show: false
        },
        axisTicks: {
          show: false
        }
      },
      yaxis: {
        show: false
      },
    };
    var chart = new ApexCharts(document.querySelector('#analytics-report-chart'), options);
    chart.render();
  })();

}

function sum(arr) {

  console.log(arr);

  var element = 0;
  for (let i = 0; i < arr.length; i++) {
    element = element + arr[i];

    console.log(element);
    return element;
  }
}
window.onload = function () {
    resetAllSlides();
}

function resetAllSlides() {
    popSlide1();
    popSlide2();
    areaSlide1();
    areaSlide2();
    ageSlide1();
    ageSlide2();
    mortgageSlide1();
    mortgageSlide2();
    incomeSlide1();
    incomeSlide2();
    rentSlide1();
    rentSlide2();
}

function popSlide1() {
    slide1(document.getElementById("slider-pop1"),
        document.getElementById("slider-pop2"),
        document.querySelector("input[name='min-population']"),
        document.getElementById("slider-pop1").max,
        document.querySelector(".slider-track-pop")
    );
}

function popSlide2() {
    slide2(document.getElementById("slider-pop1"),
        document.getElementById("slider-pop2"),
        document.querySelector("input[name='max-population']"),
        document.getElementById("slider-pop1").max,
        document.querySelector(".slider-track-pop")
    );
}

document.getElementById("min-population").addEventListener('change', function () {
    document.getElementById("slider-pop1").value = this.value;
    popSlide1();
});

document.getElementById("max-population").addEventListener('change', function () {
    document.getElementById("slider-pop2").value = this.value;
    popSlide2();
});

function areaSlide1() {
    slide1(document.getElementById("slider-area1"),
        document.getElementById("slider-area2"),
        document.querySelector("input[name='min-area']"),
        document.getElementById("slider-area1").max,
        document.querySelector(".slider-track-area")
    );
}

function areaSlide2() {
    slide2(document.getElementById("slider-area1"),
        document.getElementById("slider-area2"),
        document.querySelector("input[name='max-area']"),
        document.getElementById("slider-area1").max,
        document.querySelector(".slider-track-area")
    );
}

document.getElementById("min-area").addEventListener('change', function () {
    document.getElementById("slider-area1").value = this.value;
    areaSlide1();
});

document.getElementById("max-area").addEventListener('change', function () {
    document.getElementById("slider-area2").value = this.value;
    areaSlide2();
});

function ageSlide1() {
    slide1(document.getElementById("slider-age1"),
        document.getElementById("slider-age2"),
        document.querySelector("input[name='min-age']"),
        document.getElementById("slider-age1").max,
        document.querySelector(".slider-track-age")
    );
}

function ageSlide2() {
    slide2(document.getElementById("slider-age1"),
        document.getElementById("slider-age2"),
        document.querySelector("input[name='max-age']"),
        document.getElementById("slider-age1").max,
        document.querySelector(".slider-track-age")
    );
}

document.getElementById("min-age").addEventListener('change', function () {
    document.getElementById("slider-age1").value = this.value;
    ageSlide1();
});

document.getElementById("max-age").addEventListener('change', function () {
    document.getElementById("slider-age2").value = this.value;
    ageSlide2();
});

function mortgageSlide1() {
    slide1(document.getElementById("slider-mortgage1"),
        document.getElementById("slider-mortgage2"),
        document.querySelector("input[name='min-mortgage']"),
        document.getElementById("slider-mortgage1").max,
        document.querySelector(".slider-track-mortgage")
    );
}

function mortgageSlide2() {
    slide2(document.getElementById("slider-mortgage1"),
        document.getElementById("slider-mortgage2"),
        document.querySelector("input[name='max-mortgage']"),
        document.getElementById("slider-mortgage1").max,
        document.querySelector(".slider-track-mortgage")
    );
}

document.getElementById("min-mortgage").addEventListener('change', function () {
    document.getElementById("slider-mortgage1").value = this.value;
    mortgageSlide1();
});

document.getElementById("max-mortgage").addEventListener('change', function () {
    document.getElementById("slider-mortgage2").value = this.value;
    mortgageSlide2();
});

function incomeSlide1() {
    slide1(document.getElementById("slider-income1"),
        document.getElementById("slider-income2"),
        document.querySelector("input[name='min-income']"),
        document.getElementById("slider-income1").max,
        document.querySelector(".slider-track-income")
    );
}

function incomeSlide2() {
    slide2(document.getElementById("slider-income1"),
        document.getElementById("slider-income2"),
        document.querySelector("input[name='max-income']"),
        document.getElementById("slider-income1").max,
        document.querySelector(".slider-track-income")
    );
}

document.getElementById("min-income").addEventListener('change', function () {
    document.getElementById("slider-income1").value = this.value;
    incomeSlide1();
});

document.getElementById("max-income").addEventListener('change', function () {
    document.getElementById("slider-income2").value = this.value;
    incomeSlide2();
});

function rentSlide1() {
    slide1(document.getElementById("slider-rent1"),
        document.getElementById("slider-rent2"),
        document.querySelector("input[name='min-rent']"),
        document.getElementById("slider-rent1").max,
        document.querySelector(".slider-track-rent")
    );
}

function rentSlide2() {
    slide2(document.getElementById("slider-rent1"),
        document.getElementById("slider-rent2"),
        document.querySelector("input[name='max-rent']"),
        document.getElementById("slider-rent1").max,
        document.querySelector(".slider-track-rent")
    );
}

document.getElementById("min-rent").addEventListener('change', function () {
    document.getElementById("slider-rent1").value = this.value;
    rentSlide1();
});

document.getElementById("max-rent").addEventListener('change', function () {
    document.getElementById("slider-rent2").value = this.value;
    rentSlide2();
});

function slide1(slider1, slider2, inputVal1, sliderMaxValue, sliderTrack) {
    if (parseInt(slider2.value) - parseInt(slider1.value) <= 0) {
        slider1.value = parseInt(slider2.value);
    }
    // displayVal1.textContent = slider1.value;
    inputVal1.value = slider1.value;
    fillColor(slider1, slider2, sliderMaxValue, sliderTrack);
}

function slide2(slider1, slider2, inputVal2, sliderMaxValue, sliderTrack) {
    if (parseInt(slider2.value) - parseInt(slider1.value) <= 0) {
        slider2.value = parseInt(slider1.value);
    }
    // displayVal2.textContent = slider2.value;
    inputVal2.value = slider2.value;
    fillColor(slider1, slider2, sliderMaxValue, sliderTrack);
}

function fillColor(slider1, slider2, sliderMaxValue, sliderTrack) {
    let Percent1 = (slider1.value / sliderMaxValue) * 100;
    let Percent2 = (slider2.value / sliderMaxValue) * 100;
    sliderTrack.style.background = `linear-gradient(to right, #dadae5 ${Percent1}% , #3264fe ${Percent1}% , #3264fe ${Percent2}%, #dadae5 ${Percent2}%)`;
}

document.getElementById("reset-button").addEventListener('click', function () {
    document.getElementById('form').reset();
    resetAllSlides();
});

document.getElementById("sum").addEventListener('click', function () {

    document.getElementById("year").style.display = 'block';
    document.getElementById("display-controller-sort").style.display = 'none';
    document.getElementById("homeless").style.display = 'block';
    document.getElementById("compare-sort").style.display = 'none';
    document.getElementById("percentage").style.display = 'none';
    document.getElementById("group").style.display = 'none';
});
document.getElementById("detail").addEventListener('click', function () {
    document.getElementById("year").style.display = 'block';
    document.getElementById("display-controller-sort").style.display = 'block';
    document.getElementById("homeless").style.display = 'block';
    document.getElementById("compare-sort").style.display = 'none';
    document.getElementById("percentage").style.display = 'none';
    document.getElementById("group").style.display = 'block';
});

document.getElementById("compare").addEventListener('click', function () {
    document.getElementById("year").style.display = 'none';
    document.getElementById("display-controller-sort").style.display = 'none';
    document.getElementById("homeless").style.display = 'none';
    document.getElementById("compare-sort").style.display = 'block';
    document.getElementById("percentage").style.display = 'block';
    document.getElementById("group").style.display = 'block';
});

document.getElementById("sorting1").addEventListener('change', function () {
    if (this.value === '') {
        document.getElementById("more-sorting").style.display = 'none';
        document.getElementById('sorting2').value = "";
        document.getElementById('descending2').checked = false;
    } else {
        document.getElementById("more-sorting").style.display = 'block';

        let sort1_value = this.value;
        document.querySelectorAll('select[name="sorting2"] option').forEach(function (element) {
            if (element.value === sort1_value) {
                element.style.display = 'none';
            } else {
                element.style.display = 'block';
            }
        });
    }
});

document.getElementById("age-range").addEventListener('change', function () {
    if (this.checked) {
        document.getElementById("age-range-filter").style.display = 'block';
    } else {
        document.getElementById("age-range-filter").style.display = 'none';
    }
});

document.querySelectorAll('input[name="selector"]').forEach(function (element) {
    element.addEventListener('change', function () {
        document.getElementById("data-entry-filter-panel").style.display = 'block';
    });
});





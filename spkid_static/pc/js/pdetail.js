
! function() {
    for (var t = window.location.hash.substr(1), a = null, e = ["evaluation", "parameter", "comment", "goodscmt"], i = 0; i < e.length; i++)
        if (-1 != t.indexOf(e[i])) {
            a = e[i];
            break
        }
    var o = null,
        n = $(".counter"),
        //s = n.counter().data("counter"),
        r = {
            _$thumbnails: $(".product-thumbnails"),
            _$primaryImg: $(".product-primary-image img"),
            lock: !1,
            setImage: function(t) {
                this._$thumbnails.html("").css("left", "-16px");
                for (var a = t.length, e = 0; a > e; e++) {
                    var i;
                    0 == e ? (this._$primaryImg.attr("src", t[e]), i = $('<div class="product-thumbnail-image active"><img src="' + t[e] + '"/></div>')) : i = $('<div class="product-thumbnail-image"><img src="' + t[e] + '"/></div>'), this._$thumbnails.append(i)
                }
                a > 4 ? ($(".product-thumbnails-window").addClass("thumbnails-small"), $(".thumbnails-control").removeClass("dn")) : ($(".product-thumbnails-window").removeClass("thumbnails-small"), $(".thumbnails-control").addClass("dn"))
            },
            hoverEvent: function() {
                var t = this;
                t._$thumbnails.on("mouseover", function(a) {
                    var e = $(a.target);
                    if (!e.hasClass("product-thumbnail-image"))
                        return !1;
                    var i = e.find("img"),
                        o = setTimeout(function() {
                            var a = i.attr("src");
                            t._$primaryImg.attr("src", a), $(".product-thumbnail-image").removeClass("active"), e.addClass("active")
                        }, 200);
                    i.on("mouseout", function() {
                        clearTimeout(o), i.off("mouseleave")
                    })
                })
            },
            slide: function(t) {
                var a = this;
                if (!this.lock) {
                    var e = this._$thumbnails.find("img").length,
                        i = parseInt(this._$thumbnails.css("left"), 10),
                        o = this._$thumbnails.find(".product-thumbnail-image").outerWidth(),
                        n = parseInt(this._$thumbnails.find(".product-thumbnail-image").css("padding-left"), 10),
                        s = i + o * t; - 1 * n >= s && s >= (e - 4) * o * -1 - n && (this.lock = !0, this._$thumbnails.animate({
                        left: s
                    }, 200, function() {
                        a.lock = !1
                    }))
                }
            },
            switchEvent: function() {
                var t = this;
                $(".thumbnails-control").click(function() {
                    t.slide($(this).hasClass("thumbnails-left") ? 1 : -1)
                })
            },
            init: function() {
                this.hoverEvent(), this.switchEvent()
            }
        }
        
       
        v = {},
        
        b = function() {
            var t = $(".navbar-placeholder"),
                a = $(".navbar-wrapper"),
                e = t.offset();
            $(window).scroll(function() {
                var t = $(document).scrollTop();
                e.top > t && a.hasClass("fixed") ? a.removeClass("fixed") : e.top < t && !a.hasClass("fixed") && a.addClass("fixed")
            }), $(".navbar li a").click(function() {
                var t = $(this),
                    a = t.data("tag");
                return t.hasClass("disabled") ? !1 : ($(".navbar li a").removeClass("active"), t.addClass("active"), void g(a, function() {
                    $("html,body").animate({
                        scrollTop: e.top
                    })
                }))
            })
        }
    $(document).ready(function() {
        $("img.lazy").lazyload(), a ? "goodscmt" == a ? ($("[data-tag=comment]").click(), $("[data-type=goodscomment]").click()) : $("[data-tag=" + a + "]").click() : r.init()
    })
}();
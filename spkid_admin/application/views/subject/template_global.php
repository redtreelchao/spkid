<?php print $header; ?>

<script type="text/javascript">
    //<![CDATA[
    window.onload = function() {
        // 更改页面title
        document.title = '<?php print $subject->subject_title; ?>';
    };
    //]]>
</script>
<script type="text/javascript">
	$(function(){
		$("img[fslazy]").lazyload({
			threshold: 200,
			effect: "fadeIn"
		})
	});
</script>
<link href="<?=STATIC_HOST?>/css/default.css" type="text/css" rel="stylesheet" />
<link href="<?=STATIC_HOST?>/css/plist.css" type="text/css" rel="stylesheet" />

<?php foreach ($template_content_list as $content): ?>
    <?=$content; ?>
<?php endforeach; ?>

<?php print $footer; ?>


<script type="text/javascript">
jQuery.ajaxTransport( "script", function(s) {

    // This transport only deals with cross domain requests
    if ( s.crossDomain ) {

        var script,
            head = document.head || document.getElementsByTagName( "head" )[0] || document.documentElement;

        return {
            send: function( _, callback ) {
                script = document.createElement( "script" );
                script.async = "async";
                if ( s.scriptCharset ) {
                    script.charset = s.scriptCharset;
                }
                script.src = s.url;
                script.onload = script.onreadystatechange = function( _, isAbort ) {
                    if ( isAbort || !script.readyState || /loaded|complete/.test( script.readyState ) ) {
                        script.onload = script.onreadystatechange = null;
                        if ( head && script.parentNode ) {
                            head.removeChild( script );
                        }
                        script = undefined;
                        if ( !isAbort ) {
                            callback( 200, "success" );
                        }
                    }
                };
                head.insertBefore( script, head.firstChild );
            },

            abort: function() {
                if ( script ) {
                    script.onload( 0, 1 );
                }
            }
        };
    }
}
</script>
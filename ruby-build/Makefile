.PHONY: fetch openssl ruby

OPENSSL_VERSION = 1.0.1e
RUBY_RELEASE = 2.0
RUBY_VERSION = 2.0.0-p195

TOPDIR=$(shell pwd)

fetch: .fetch.done

openssl: .openssl.done

ruby: .ruby.done

.fetch.done:
	wget -c http://www.openssl.org/source/openssl-$(OPENSSL_VERSION).tar.gz
	wget -c http://ftp.ruby-lang.org/pub/ruby/$(RUBY_RELEASE)/ruby-$(RUBY_VERSION).tar.bz2
	>.fetch.done

.openssl.done: .fetch.done
	rm -rf openssl-$(OPENSSL_VERSION)
	tar -zxf openssl-$(OPENSSL_VERSION).tar.gz
	(cd openssl-$(OPENSSL_VERSION) && CC=arm-linux-androideabi-gcc MACHINE=arm ./config shared && make)
	>.openssl.done

.ruby.done: .openssl.done
	rm -rf ruby-$(RUBY_VERSION)
	tar -jxf ruby-$(RUBY_VERSION).tar.bz2
	(cd ruby-$(RUBY_VERSION) && \
		export CFLAGS="-I$(TOPDIR)/openssl-$(OPENSSL_VERSION)/include" && \
		export LDFLAGS="-L$(TOPDIR)/openssl-$(OPENSSL_VERSION)" && \
		./configure --target=arm-linux-androideabi --host=arm-linux-androideabi --prefix=$(TOPDIR)/rubyroot --enable-wide-getaddrinfo)
	(cd ruby-$(RUBY_VERSION) && patch -p1 < ../ruby.diff)
	(cd ruby-$(RUBY_VERSION) && make main)
	>.ruby.done

.ruby.install.done: .ruby.done
	rm -rf rubyroot
	(cd ruby-$(RUBY_VERSION) && make install-nodoc)
	find rubyroot -name *.so -exec arm-linux-androideabi-strip {} \;
	arm-linux-androideabi-strip rubyroot/bin/ruby
	> .ruby.install.done

